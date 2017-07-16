package com.casic.accessControl.event.manager;

import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.event.domain.Event;
import com.casic.accessControl.event.dto.EventDto;
import com.casic.accessControl.task.dto.TaskDto;
import com.casic.accessControl.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/8/11.
 */
@Service
public class EventManager extends HibernateEntityDao<Event> {
    /**
     * 根据用户和所属任务 获取事件列表
     *
     * @param taskId
     * @return
     */
    public List<EventDto> getEventsByTaskId(Long taskId) {
        List<Event> events = this.findBy("taskId", taskId);
        List<EventDto> eventDtos = EventDto.convert2EventDtoList(events);
        return eventDtos;
    }

    /**
     * 根据事件Id获取事件内容
     *
     * @param eventId
     * @return
     */
    public EventDto getEventById(Long eventId) {
        Event event = this.get(eventId);
        EventDto eventDto = EventDto.convert2EventDto(event);
        return eventDto;
    }

    /**
     * 根据事件ID删除事件信息
     *
     * @param eventId
     * @return
     */
    public boolean deleteEventById(Long eventId) {
        this.removeById(eventId);
        return true;
    }

    /**
     * 上传时间信息
     *
     * @param eventInfo
     * @param taskInfo
     * @param fileNames
     * @param fileBuffers
     * @return
     */
//    public Map saveEvent(EventDto eventInfo, TaskDto taskInfo,
//                         String[] fileNames, String[] fileBuffers) {
//        Map map = new HashMap<String, Object>();
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            String strDirPath = request.getSession().getServletContext().getRealPath("");
//
//            //TODO LIST:新建event
//            Event event = new Event();
//            //TODO LIST:保存图片
////            FileOutputStream fos = null;
//            String toDir = strDirPath + "/images/events";   //存储路径
//            String fileName = "";
//            for (int i = 0; i < fileBuffers.length; i++) {
//                if (fileBuffers[i] != null && fileNames[i] != null) {
//                    fileName += fileNames[i] + ",";
//                    byte[] buffer = new BASE64Decoder().decodeBuffer(fileBuffers[i]);   //对android传过来的图片字符串进行解码
//                    File destDir = new File(toDir);
//                    if (!destDir.exists())
//                        destDir.mkdirs();
//                    File imageFile = new File(destDir, fileNames[i]);
//                    fos = new FileOutputStream(imageFile);   //保存图片
//                    fos.write(buffer);
//                    fos.flush();
//                    fos.close();
//                }
//            }
////            for (int i = 0; i < fileBuffers.length; i++) {
////                if (fileBuffers[i] != null && fileNames[i] != null) {
////                    fileName += fileNames[i] + ",";
//////                    byte[] buffer = new BASE64Decoder().decodeBuffer(fileBuffers[i]);   //对android传过来的图片字符串进行解码
////                    File destDir = new File(toDir);
////                    if (!destDir.exists())
////                        destDir.mkdirs();
////                    File imageFile = new File(destDir, fileNames[i]);
////                    OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream(imageFile));   //保存图片
////                     BufferedReader fileReader = new BufferedReader(new FileReader(fileBuffers[i]));
////                    String str =null;
////                    while((str=fileReader.readLine())!=null){
////                        writer.append(str);
////                    }
//////                    fos.flush();
////                    fileReader.close();
////                    writer.close();
////                }
////            }
//            event.setImageName(fileName);
//            event.setLatitude(eventInfo.getLatitude() == null ? 0 : eventInfo.getLatitude());
//            event.setLongitude(eventInfo.getLongitude() == null ? 0 : eventInfo.getLongitude());
//            event.setCreateTime(eventInfo.getCreateTime().equalsIgnoreCase("") ? null : DateUtils.sdf2.parse(eventInfo.getCreateTime()));
//            event.setDescription(eventInfo.getDescription());
//            event.setTaskId(taskInfo.getId());
//            save(event);
//            map.put("uploadFile", "上传成功!" + "图片路径为：" + toDir + "//" + fileName);
//            return map;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public Map saveEvent(EventDto eventInfo,Long taskId,
                         String[] fileNames, String[] fileBuffers)
    {
        Map map = new HashMap<String, Object>();
        try
        {
            HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String strDirPath = request.getSession().getServletContext().getRealPath("");

            //TODO LIST:新建event
            Event event = new Event();
            //TODO LIST:保存图片
            FileOutputStream fos = null;
            String toDir = strDirPath+"/images/events";   //存储路径
            String fileName = "";
            for (int i = 0;i < fileBuffers.length;i++)
            {
                if (fileBuffers[i] != null&&fileNames[i]!=null)
                {
                  fileName += fileNames[i]+",";

                    byte[] buffer = new BASE64Decoder().decodeBuffer(fileBuffers[i]);   //对android传过来的图片字符串进行解码
                    File destDir = new File(toDir);
                    if(!destDir.exists())
                        destDir.mkdirs();
                    File imageFile = new File(destDir,fileNames[i]);
                    fos = new FileOutputStream(imageFile);   //保存图片
                    fos.write(buffer);
                    fos.flush();
                    fos.close();
                }
            }
            if(fileName.endsWith(",")){
                fileName = fileName.substring(0,fileName.length()-1);
            }
            event.setImageName(fileName);
            event.setLatitude(eventInfo.getLatitude()==null?0:Double.valueOf(eventInfo.getLatitude()));
            event.setLongitude(eventInfo.getLongitude()==null ? 0 : Double.valueOf(eventInfo.getLongitude()));
            event.setCreateTime(eventInfo.getCreateTime().equalsIgnoreCase("")?null:DateUtils.sdf2.parse(eventInfo.getCreateTime()));
            event.setDescription(eventInfo.getDescription());
            event.setTaskId(taskId);
            save(event);
            map.put("uploadFile", "上传成功!" + "图片路径为：" + toDir + "//" + fileName);
            return map;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
