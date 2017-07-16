package com.casic.accessControl.event;

import com.casic.accessControl.basic.BasicTest;
import com.casic.accessControl.event.domain.Event;
import com.casic.accessControl.event.dto.EventDto;
import com.casic.accessControl.event.manager.EventManager;
import com.casic.accessControl.util.DateUtils;
import com.google.gson.Gson;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/8/31.
 */
public class EventTest extends BasicTest{
    @Resource
    private EventManager eventManager;
    @Test
    public void saveEventTest(){
        Long taskId = 4L;
        EventDto eventDto = new EventDto();
        eventDto.setImageName("ImageNameTest");
        eventDto.setLatitude(Math.random() * 90.0);
        eventDto.setLongitude(Math.random() * 180.0);
        eventDto.setCreateTime(String.valueOf(new Date()));
        eventDto.setDescription("this is a event test");
        String [] fileNames = {""};
        String [] fileBuffers={""};
        Map<String,Object> res = eventManager.saveEvent(eventDto,taskId,fileNames,fileBuffers);
        System.out.println(new Gson().toJson(res));


    }

    @Test
    public void getEventsByTaskIdTest(){
       Long taskId = 4L;
      List<EventDto> eventDtoList =  eventManager.getEventsByTaskId(taskId);
       String s = new Gson().toJson(eventDtoList);
        System.out.println(s);

    }
    @Test
    public void getEventByIdTest(){
        Long eventId = 10L;
        EventDto eventDto =  eventManager.getEventById(eventId);
        String res = new Gson().toJson(eventDto);
        System.out.println(res);
    }

    @Test
    public void deleteEventByIdTest(){
        Long eventId = 10L;
        boolean res = eventManager.deleteEventById(eventId);
         System.out.println(res);
    }


}
