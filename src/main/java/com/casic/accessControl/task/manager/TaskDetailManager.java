package com.casic.accessControl.task.manager;

import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.task.domain.Task;
import com.casic.accessControl.task.domain.TaskDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2016/4/22.
 */
@Service("taskDetailManager")
public class TaskDetailManager extends HibernateEntityDao<TaskDetail> {

    public List<TaskDetail> getTaskDetailByTask(Task task) {
        Criteria criteria = this.createCriteria(TaskDetail.class);
        criteria.add(Restrictions.eq("task", task));
        List<TaskDetail> taskDetails = criteria.list();
        return taskDetails;

    }
    public List<TaskDetail> getUncheckedByTask(Task task){
        Criteria criteria = this.createCriteria(TaskDetail.class);
        criteria.add(Restrictions.eq("task", task));
        criteria.add(Restrictions.eq("isChecked",0));
        List<TaskDetail> taskDetails = criteria.list();
        return taskDetails;
    }

    public void save() {

    }
}
