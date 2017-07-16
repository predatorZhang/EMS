package com.casic.accessControl.event.dto;

import com.casic.accessControl.event.domain.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/8/11.
 */
public class EventDto {
    private Long id;
    private Long taskId;
    private String description;
    private String createTime;
    private String imageName;
    private Double latitude;
    private Double longitude;
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static EventDto convert2EventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setTaskId(event.getTaskId());
        eventDto.setId(event.getId());
        eventDto.setDescription(event.getDescription());
        eventDto.setCreateTime(event.getCreateTime().toString());
        eventDto.setImageName(event.getImageName());
        eventDto.setLatitude(event.getLatitude());
        eventDto.setLongitude(event.getLongitude());
        eventDto.setStatus(event.getStatus());
        return eventDto;
    }

    public static List<EventDto> convert2EventDtoList(List<Event> eventList){
        List<EventDto> result = new ArrayList<EventDto>();
        for(Event event:eventList){
            result.add(convert2EventDto(event));
        }
        return result;
    }
}
