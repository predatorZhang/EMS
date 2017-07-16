package com.casic.accessControl.task.dto;

import com.casic.accessControl.task.domain.Position;
import com.casic.accessControl.task.domain.Task;
import com.casic.accessControl.user.domain.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/13.
 */
public class PositionDto {
    private Long id;
    private double longitude;
    private double latitude;
    private String localTime;
    private String patrolerId;
    private String patrolerName;
    private String taskId;
    private String taskCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getPatrolerId() {
        return patrolerId;
    }

    public void setPatrolerId(String patrolerId) {
        this.patrolerId = patrolerId;
    }

    public String getPatrolerName() {
        return patrolerName;
    }

    public void setPatrolerName(String patrolerName) {
        this.patrolerName = patrolerName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public static PositionDto Convert(Position position)
    {
        if (position == null) {
            return null;
        }
        PositionDto positionDto=new PositionDto();
        positionDto.setId(position.getId());
        positionDto.setLatitude(position.getLatitude());
        positionDto.setLongitude(position.getLongitude());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (position.getLocalTime() != null)
            positionDto.setLocalTime(sdf.format(position.getLocalTime()));

        Task task = position.getTask();
        if(task!=null)
        {
            positionDto.setTaskId(task.getId() + "");
            positionDto.setTaskCode(task.getTaskCode());
        }
        User patroler = position.getPatroler();
        if(patroler!=null)
        {
            positionDto.setPatrolerId(patroler.getId() + "");
            positionDto.setPatrolerName(patroler.getUserName());
        }
        return positionDto;
    }
    public static List<PositionDto> Converts(List<Position> positions)
    {
        List<PositionDto> positionDtos= new ArrayList<PositionDto>();
        for (Position position : positions)
        {
            positionDtos.add(PositionDto.Convert(position));
        }
        return positionDtos;
    }
}
