package com.casic.accessControl.rs;

import com.casic.accessControl.marker.dto.BusinessDataDto;
import com.casic.accessControl.marker.manager.BusinessDataManager;
import com.casic.accessControl.task.domain.Task;
import com.casic.accessControl.task.domain.TaskDetail;
import com.casic.accessControl.task.dto.TaskDto;
import com.casic.accessControl.task.manager.TaskDetailManager;
import com.casic.accessControl.task.manager.TaskManager;
import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.Role;
import com.casic.accessControl.user.domain.User;
import com.casic.accessControl.user.manager.RoleManager;
import com.casic.accessControl.user.manager.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by admin on 2015/1/15.
*/
@Component
@Path("task")
public class TaskResource
{
    private static Logger logger = LoggerFactory.getLogger(TaskResource.class);

    @Resource
    private UserManager userManager;

    @Resource
    private RoleManager roleManager;

    @Resource
    private TaskManager taskManager;

    @Resource
    private TaskDetailManager taskDetailManager;
    @Resource
    private BusinessDataManager businessDataManager;

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Map get(@QueryParam("userName") String userName ){
        Map map = new HashMap<String, Object>();
        try {

            Role role = roleManager.getRoleByType(5);
            User user = userManager.getPatrolerByName(userName, role);
            if (user == null) {
                map.put("success", "false");
                map.put("message", "人员不存在！");
                return map;
            }
            List<Task> tasks = taskManager.getTasksByPatroler(user);
            List<TaskInfo> taskInfos = TaskInfo.Converts(tasks);
            if (taskInfos!=null)
            {
                map.put("success", "true");
                map.put("message", taskInfos);
            }
        }
        catch (Exception e)
        {
            map.put("success", "false");
            map.put("message", e.getMessage());
        }
        return  map;
    }

    @GET
    @Path("post")
    @Produces(MediaType.APPLICATION_JSON)
    public Map post(@QueryParam("taskId") String taskId ){
        Map map = new HashMap<String, Object>();
        try {
            Task task = taskManager.get(Long.parseLong(taskId));
            if (task!=null)
            {
                task.setStatus(2);
                task.setEndDate(new Date());
                taskManager.save(task);
                map.put("success", "true");
                map.put("message", "");
            }
        }
        catch (Exception e)
        {
            map.put("success", "false");
            map.put("message", e.getMessage());
        }
        return  map;
    }

    @GET
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    public Map updateTask(@QueryParam("taskId") String taskId,
                          @QueryParam("state") String state ){
        Map map = new HashMap<String, Object>();
        try {
            Task task = taskManager.get(Long.parseLong(taskId));
            if (task!=null)
            {
                task.setStatus(Integer.parseInt(state));
                if(state.trim().equals("1")){
                    task.setBeginDate(new Date());
                }else if(state.trim().equals("2")){
                    task.setEndDate(new Date());
                }

                taskManager.save(task);
                map.put("success", "true");
                map.put("message", "");
            }
        }
        catch (Exception e)
        {
            map.put("success", "false");
            map.put("message", e.getMessage());
        }
        return  map;
    }

    @GET
    @Path("update-task-mark")
    @Produces(MediaType.APPLICATION_JSON)
    public Map updateTaskDetail(@QueryParam("taskDetailId") String taskDetailId ){
        Map map = new HashMap<String, Object>();
        try {
            TaskDetail taskDetail = taskDetailManager.get(Long.parseLong(taskDetailId));
            if (taskDetail!=null)
            {
                taskDetail.setIsChecked(1);
                taskDetail.setFinishTime(new Date());
                taskDetailManager.save(taskDetail);
//                Task task = taskDetail.getTask();
//                //判断如果该taskid没有了未巡检的，则将task表对应的赋值为2，否则赋值为1
//                List<TaskDetail> taskDetails = taskDetailManager.getUncheckedByTask(task);
//                if(CollectionUtils.isEmpty(taskDetails)){
//                    task.setStatus(2);
//                    task.setEndDate(new Date());
//                }else{
//                    task.setStatus(1);
//                }
//                taskManager.save(task);

                map.put("success", "true");
                map.put("message", "");
            }
        }
        catch (Exception e)
        {
            map.put("success", "false");
            map.put("message", e.getMessage());
        }
        return  map;
    }

    @GET
    @Path("save-business-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> saveBusinessData(@QueryParam("taskDetailId")String taskDetailId,
                                                @QueryParam("businessData") String businessData) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("success",false);
        BusinessDataDto businessDataDto = new Gson().fromJson(businessData,new TypeToken<BusinessDataDto>() {
        }.getType());
        TaskDetail taskDetail = taskDetailManager.get(Long.parseLong(taskDetailId));
        if(taskDetail==null) return result;
        taskDetail.setIsChecked(1);
        taskDetail.setFinishTime(new Date());
        taskDetailManager.save(taskDetail);
        businessDataDto.setMarkerId(String.valueOf(taskDetail.getMarkerIdReal()));//前端未传markerId，在此关联
        boolean flag = businessDataManager.saveBusinessData(businessDataDto);
        if(!flag) return  result;
        result.put("success",true);
        return result;
    }
}
