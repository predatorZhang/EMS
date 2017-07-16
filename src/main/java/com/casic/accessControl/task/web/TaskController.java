package com.casic.accessControl.task.web;

import com.casic.accessControl.marker.domain.Marker;
import com.casic.accessControl.marker.dto.BusinessDataDto;
import com.casic.accessControl.marker.dto.MarkerDto;
import com.casic.accessControl.marker.manager.BusinessDataManager;
import com.casic.accessControl.marker.manager.MarkerManager;
import com.casic.accessControl.task.domain.Task;
import com.casic.accessControl.task.domain.TaskDetail;
import com.casic.accessControl.task.dto.TaskDetailDto;
import com.casic.accessControl.task.dto.TaskDto;
import com.casic.accessControl.task.dto.TaskStatus;
import com.casic.accessControl.task.manager.TaskDetailManager;
import com.casic.accessControl.task.manager.TaskManager;
import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.Role;
import com.casic.accessControl.user.domain.User;
import com.casic.accessControl.user.dto.UserDto;
import com.casic.accessControl.user.manager.RoleManager;
import com.casic.accessControl.user.manager.UserManager;
import com.casic.accessControl.util.DataTable;
import com.casic.accessControl.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016/4/13.
 */
@Controller
@RequestMapping("task")
public class TaskController {

    @Resource
    private TaskManager taskManager;

    @Resource
    private MarkerManager markerManager;
    @Resource
    private BusinessDataManager businessDataManager;

    @Resource
    private TaskDetailManager taskDetailManager;

    @Resource
    private UserManager userManager;

    @Resource
    private RoleManager roleManager;

//    @Resource
//    private FeatureManager featureManager;

    @RequestMapping("task-info-list")
    public void list(@RequestParam(required = true) String jsonParam, @RequestParam(required = false) Integer status, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, HttpServletResponse response, HttpSession session) {
        try {

            User user = (User) session.getAttribute(StringUtils.SYS_USER);
            DataTable<TaskDto> taskDtoDataTable = null;
            taskDtoDataTable = taskManager.pageTask(jsonParam, status, startDate, endDate, user);
            Gson gson = new Gson();
            String json = gson.toJson(taskDtoDataTable);
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("get-task")
    @ResponseBody
    public Map<String, Object> getTask(HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            User user = (User) session.getAttribute(StringUtils.SYS_USER);
            List<TaskDto> taskDtos = taskManager.getTasks(user);
            result.put("data", taskDtos);
            result.put("success", true);
            result.put("message", "获取列表成功");
        } catch (Exception e) {
            result.put("message", "获取工单失败");
            result.put("success", false);
        }
        return result;
    }

    @RequestMapping("task-info-edit")
    public String edit(@RequestParam(value = "id", required = false) Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute(StringUtils.SYS_USER);

        TaskDto taskDto = new TaskDto();
        if (id != null) {
            taskDto = TaskDto.Convert(taskManager.get(id));
        }
        Company company = currentUser.getCompany();
        Role role = roleManager.getRoleByType(5);
        List<User> users = userManager.getPatrolerByCompany(company, role);
        List<UserDto> userDtos = UserDto.Converts(users);
        model.addAttribute("model", taskDto);
        model.addAttribute("users", userDtos);
        return "task/task-info-edit";
    }

    @RequestMapping("task-info-save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute TaskDto taskDto, HttpSession session) {
        //分为新增用户和编辑两种，区分id是否为空
        User currentUser = (User) session.getAttribute(StringUtils.SYS_USER);
        Map<String, Object> map = new HashMap<String, Object>();
        if (taskDto != null) {
            Task task = new Task();
            if (taskDto.getId() != null) {
                task.setId(taskDto.getId());
            }
            User patroler = userManager.get(Long.parseLong(taskDto.getPatrolerId()));
            task.setCreator(currentUser);
            task.setPatroler(patroler);
            task.setDeployDate(new Date());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String taskCode = sdf.format(new Date());
            task.setTaskCode(taskCode);
            task.setDescription(taskDto.getDescription());
            task.setStatus(0);
            task.setIsValid(1);
            task.setCompany(currentUser.getCompany());
            taskManager.save(task);
            if (taskDto.getId() == null) {//新增
                map.put("success", true);
                map.put("message", "新增工单成功");
            } else {
                map.put("success", true);
                map.put("message", "编辑工单成功");
            }
        } else {
            map.put("success", false);
            map.put("message", "工单信息为空");
        }
        return map;
    }

    @RequestMapping("task-Save")
    @ResponseBody
    public Map<String, Object> saveTask(@RequestParam(value = "markers", required = true) String markerIds,
                                        @RequestParam(value = "patrolerId", required = true) String patrolerId,
                                        @RequestParam(value = "description", required = true) String description,
                                        HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User currentUser = (User) session.getAttribute(StringUtils.SYS_USER);
            if (currentUser == null) {
                map.put("success", false);
                map.put("message", "用户未登录");
                return map;
            }
            Task task = new Task();
            User patroler = userManager.get(Long.parseLong(patrolerId));
            List<Marker> markers = new ArrayList<Marker>();
//            if (areaId.equalsIgnoreCase(""))//绘制工单
//            {
//                markers = markerManager.getMarkerByIds(markerIds);
//            } else//按区域下发工单
//            {
//                Feature feature = null;
//                Feature line = null;
//                if (StringUtils.isNotBlank(areaId)) {
//                    feature = featureManager.get(Long.parseLong(areaId));
//                }
//                if (StringUtils.isNotBlank(lineId)) {
//                    line = featureManager.get(Long.valueOf(lineId));
//                }
//                markers = markerManager.getMarkerByFeature(feature, line);
//            }
            markers = markerManager.getMarkerByIds(markerIds);
            if (markers == null || markers.size() == 0) {
                map.put("success", false);
                map.put("message", "未包含标识点！");
                return map;
            }
            task.setCreator(currentUser);
            task.setPatroler(patroler);
            task.setDeployDate(new Date());
            task.setCompany(currentUser.getCompany());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String taskCode = sdf.format(new Date());
            task.setTaskCode(taskCode);
            task.setDescription(description);
            task.setStatus(0);
            task.setIsValid(1);
            List<TaskDetail> taskDetails = new ArrayList<TaskDetail>();
            for (Marker m : markers) {
//                if(m.getMarkerId()==null) continue;
                TaskDetail taskDetail = new TaskDetail();
//                taskDetail.setMarkerName(m.getMarkerName());
                taskDetail.setMarkerId(m.getId());
                taskDetail.setMarkerIdReal(m.getMarkerId());
                taskDetail.setLatitude(m.getLatitude());
                taskDetail.setLongitude(m.getLongitude());
                taskDetail.setTask(task);
                taskDetail.setIsNormal(1);
                taskDetail.setIsChecked(0);
                taskDetails.add(taskDetail);
            }
            task.setTaskDetails(taskDetails);
            taskManager.save(task);
            for (int i = 0; i < taskDetails.size(); i++) {
                taskDetailManager.save(taskDetails.get(i));
            }
            map.put("success", true);
            map.put("message", "添加工单成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("get-task-markers")
    @ResponseBody
    public Map<String, Object> getMarkersByTaskId(@RequestParam(value = "taskId", required = true) String taskId,
                                                  HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            User user = (User) session.getAttribute(StringUtils.SYS_USER);
            Task task = taskManager.getTaskById(taskId);
            List<TaskDetail> taskDetails = taskDetailManager.getTaskDetailByTask(task);
            List<TaskDetailDto> taskDetailDtos = TaskDetailDto.Converts(taskDetails);
//            String markerId = "";
//            if (taskDetails!=null)
//            {
//                for (TaskDetail t:taskDetails)
//                {
//                    markerId = markerId+t.getMarkerId().toString()+",";
//                }
//            }
//            List<Marker> markers = markerManager.getMarkerByIds(markerId);
//            List<MarkerDto> markerDtos= MarkerDto.convert2MarkerDtoList(markers);
            result.put("data", taskDetailDtos);
            result.put("success", true);
            result.put("message", "获取marker成功");
        } catch (Exception e) {
            result.put("message", "获取marker失败");
            result.put("success", false);
        }
        return result;
    }

    @RequestMapping("getTaskCount")
    @ResponseBody
    public Map<String, Object> getTaskCount(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, HttpSession session) {
        Map<String, Object> res = new HashMap<String, Object>();
        User user = (User) session.getAttribute(StringUtils.SYS_USER);
        Company company = user.getCompany();
//        Map<String,Long> data = taskManager.getStatusCountMap(company);
        List<TaskStatus> data = taskManager.getTaskStatus(user, startDate, endDate);
        res.put("data", data);
        res.put("message", "任务成功获取");
        res.put("success", true);
        return res;


    }
}
