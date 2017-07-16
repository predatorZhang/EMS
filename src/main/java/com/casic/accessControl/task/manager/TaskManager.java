package com.casic.accessControl.task.manager;

import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.core.page.Page;
import com.casic.accessControl.core.util.StringUtils;
import com.casic.accessControl.task.domain.Task;
import com.casic.accessControl.task.dto.TaskDto;
import com.casic.accessControl.task.dto.TaskStatus;
import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.User;
import com.casic.accessControl.user.dto.UserDto;
import com.casic.accessControl.util.DataTable;
import com.casic.accessControl.util.DataTableParameter;
import com.casic.accessControl.util.DataTableUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016/4/22.
 */
@Service("taskManager")
public class TaskManager extends HibernateEntityDao<Task> {



    public DataTable<TaskDto> pageTask(String params, Integer status, String startDate, String endDate, User user) {
        if (StringUtils.isBlank(startDate)) {
            startDate = "0000-00-00 00:00:00";//yyyy-mm-dd
        } else {
            startDate = startDate + " 00:00:00";
        }
        if (StringUtils.isBlank(endDate)) {//限定一个最大值
            endDate = "9999-12-31 00:00:00";
        } else {
            endDate = endDate + " 23:59:59";
        }
        Date dateBegin = null;
        Date dateEnd = null;
        try {
            dateBegin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
            dateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DataTable<TaskDto> result = new DataTable<TaskDto>();
        DataTableParameter parameter = DataTableUtils.getDataTableParameterByJsonParam(params);
        int start = parameter.getiDisplayStart();
        int pageSize = parameter.getiDisplayLength();
        int pageNo = (start / pageSize) + 1;
        Criteria criteria = this.createCriteria(Task.class);
        criteria.addOrder(Order.desc("id"));
        criteria.add(Restrictions.eq("creator.id", user.getId())).add(Restrictions.eq("isValid",1));
        criteria.add(Restrictions.between("deployDate", dateBegin, dateEnd));
        if (status != null) {
            criteria.add(Restrictions.eq("status", status));
        }

        if (StringUtils.isNotBlank(parameter.getsSearch())) {
            criteria.add(Restrictions.like("taskCode", "%" + parameter.getsSearch() + "%"));
        }
        Page page = pagedQuery(criteria, pageNo, pageSize);

        List<TaskDto> taskDtos = TaskDto.Converts((List<Task>) page.getResult());
        result.setAaData(taskDtos);
        result.setiTotalDisplayRecords((int) page.getTotalCount());
        result.setiTotalRecords((int) page.getTotalCount());
        result.setsEcho(parameter.getsEcho());
        return result;
    }

    public Task getTaskById(String taskId) {
        Long id = Long.parseLong(taskId);
        Criteria criteria = this.createCriteria(Task.class);
        criteria.add(Restrictions.eq("id", id));
        List<Task> tasks = criteria.list();
        if (tasks != null && tasks.size() > 0)
            return tasks.get(0);
        return null;
    }

//    @Cacheable(value = "getTasks", key = "#user.getId()")
    public List<TaskDto> getTasks(User user) {
        Criteria criteria = this.createCriteria(Task.class);
        criteria.addOrder(Order.desc("id"));
        criteria.add(Restrictions.eq("creator.id", user.getId()));
        List<TaskDto> result = TaskDto.Converts(criteria.list());
        return result;
    }

//    @Cacheable(value = "getTasksByPatroler", key = "#user.getId()")
    public List<Task> getTasksByPatroler(User user) {
        Criteria criteria = this.createCriteria(Task.class);
        criteria.addOrder(Order.desc("id"));
        criteria.add(Restrictions.eq("patroler.id", user.getId()));
        criteria.add(Restrictions.eq("status",0));
        List<Task> result = criteria.list();
        for (Task task : result) {
            task.getTaskDetails().toString();
        }
        return result;
    }

    /**
     * 获取用户权限范围内的所有任务统计信息
     *
     * @param user
     * @return
     */
    public List<TaskStatus> getTaskStatus(User user, String startDate, String endDate) {
        if (StringUtils.isBlank(startDate)) {
            startDate = "0000-00-00 00:00:00";//yyyy-mm-dd
        } else {
            startDate = startDate + " 00:00:00";
        }
        if (StringUtils.isBlank(endDate)) {//限定一个最大值
            endDate = "9999-12-31 00:00:00";
        } else {
            endDate = endDate + " 23:59:59";
        }
        Query query = this.getSession().createSQLQuery("select status,count(1) as statusCount from Task where company =" + user.getCompany().getId() + " and deployDate between '" + startDate + "' and '" + endDate + "' group by status").addScalar("status", StandardBasicTypes.STRING).addScalar("statusCount", StandardBasicTypes.LONG);
        List<TaskStatus> taskStatuses = new ArrayList<TaskStatus>();
        List list = query.list();
        for (int i = 0; i < list.size(); i++) {
            TaskStatus taskStatus = new TaskStatus();
            Object[] objList = (Object[]) query.list().get(i);
            taskStatus.setStatus((String) objList[0]);
            taskStatus.setStatusCount((Long) objList[1]);
            taskStatuses.add(taskStatus);
        }

        return taskStatuses;

    }

//    /**
//     * 获取租户范围下的特定状态的任务个数
//     *
//     * @param status
//     * @param company
//     * @return
//     */
//    public Long getCountByTaskStatus(Integer status, Company company) {
//        Long count = (Long) this.find("select count(1) from Task where status=" + status + " and isValid=1 and company = " + company.getId()).get(0);
//        return count;
//    }

//    /**
//     * 现在这个写法不合适，硬编码，上面的group by没有成功，暂时这样了
//     *
//     * @param company
//     * @return
//     */
//    public Map<String, Long> getStatusCountMap(Company company) {
//        Long undoCount = getCountByTaskStatus(0, company);
//        Long doingCount = getCountByTaskStatus(1, company);
//        Long doneCount = getCountByTaskStatus(2, company);
//        Long total = undoCount + doingCount + doneCount;
//        Map<String, Long> res = new HashMap<String, Long>();
//        res.put("undoCount", undoCount);
//        res.put("doingCount", doingCount);
//        res.put("doneCount", doneCount);
//        res.put("totalCount", total);
//        return res;
//    }


}
