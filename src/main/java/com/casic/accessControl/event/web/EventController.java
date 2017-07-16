package com.casic.accessControl.event.web;

import com.casic.accessControl.event.dto.EventDto;
import com.casic.accessControl.event.manager.EventManager;
import com.casic.accessControl.task.dto.TaskDto;
import com.casic.accessControl.task.manager.TaskManager;
import com.casic.accessControl.user.domain.User;
import com.casic.accessControl.util.StringUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/8/11.
 */
@Controller
@RequestMapping("event")
public class EventController {

    @Resource
    private EventManager eventManager;
    @Resource
    private TaskManager taskManager;

    @RequestMapping("get-event-list")
    @ResponseBody
    public Map<String, Object> getEventListByTaskId(@RequestParam(value = "taskId") Long taskId, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = (User) session.getAttribute(StringUtils.SYS_USER);//获取当前登录用户
        if (user == null) {
            result.put("data", null);
            result.put("message", "用户未登录");
            result.put("success", "false");
            return result;
        }
        List<EventDto> eventList = eventManager.getEventsByTaskId(taskId);
        result.put("data", eventList);
        result.put("message", "成功获取事件列表");
        result.put("success", "true");
        return result;
    }

    @RequestMapping("save-event")
    @ResponseBody
//    @GET
//    @Path("save-event")
//    @Produces(MediaType.APPLICATION_JSON)
    public Map post(@RequestParam(value = "taskId",required = true) String taskId,
                    @RequestParam(value = "fileBuffer1",required = false) MultipartFile fileBuffer1,
                    @RequestParam(value = "fileBuffer2",required = false) MultipartFile fileBuffer2,
                    @RequestParam(value = "fileBuffer3",required = false) MultipartFile fileBuffer3,
                    @RequestParam(value = "data",required = true) String eventInfo)
    {

        Map result = new HashMap<String, Object>();
        if(StringUtils.isBlank(taskId)){
            result.put("message","请先执行工单，再进行事件上传！");
            result.put("success",false);
            return result;
        }
        try
        {
            String [] fileNames = new String[3];
            String [] fileBuffers = new String[3];
            if (fileBuffer1!=null)
            {
                fileNames[0]=fileBuffer1.getOriginalFilename();
                byte[] fis = fileBuffer1.getBytes();
                fileBuffers[0] = new String(Base64.encode(fis, Base64.BASE64DEFAULTLENGTH));
            }
            if (fileBuffer2!=null)
            {
                fileNames[1]=fileBuffer2.getOriginalFilename();
                byte[] fis = fileBuffer2.getBytes();
                fileBuffers[1] = new String(Base64.encode(fis,Base64.BASE64DEFAULTLENGTH));
            }
            if (fileBuffer3!=null)
            {
                fileNames[2]=fileBuffer3.getOriginalFilename();
                byte[] fis = fileBuffer3.getBytes();
                fileBuffers[2] = new String(Base64.encode(fis,Base64.BASE64DEFAULTLENGTH));
            }

            Gson gson = new Gson();
            EventDto eventDto = gson.fromJson(eventInfo, EventDto.class);
//            TaskDto taskDto = TaskDto.Convert(taskManager.getTaskById(taskId.trim()));
            Long taskIdValue = 0L;
            try{
                 taskIdValue = Long.valueOf(taskId);
            }catch(NumberFormatException e){//传过来的任务ID无法转换为Long类型
                result.put("success", false);
                result.put("message", "请确认已经执行任务，或任务ID号正确！");
                return result;

           }
            Map temp = eventManager.saveEvent(eventDto, taskIdValue, fileNames,fileBuffers);
            if (temp!=null)
            {
                result.put("success", true);
                result.put("message", temp.get("uploadFile"));
            }
            else
            {
                result.put("success", false);
                result.put("message", "上传失败");
            }
        } catch (Exception e)
        {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }


}
