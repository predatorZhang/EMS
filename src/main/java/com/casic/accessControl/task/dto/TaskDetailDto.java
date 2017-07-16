package com.casic.accessControl.task.dto;

import com.casic.accessControl.task.domain.Task;
import com.casic.accessControl.task.domain.TaskDetail;
import com.casic.accessControl.user.domain.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/13.
 */
public class TaskDetailDto {
    private Long id;
    private String taskId;
    private String taskCode;
    private String markerName;//关联点的信息
    private Long markerId;
    private Double longitude;//经度
    private Double latitude;//纬度
    private Integer isChecked;//是否巡检完成
    private Integer isNormal;
    private String imagePath;
    private String description;
    private String finishTime;
    private String markerIdReal;//物理上的标签ID，在移动端需要使用

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public Long getMarkerId() {
        return markerId;
    }

    public void setMarkerId(Long markerId) {
        this.markerId = markerId;
    }

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    public Integer getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Integer isNormal) {
        this.isNormal = isNormal;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getMarkerIdReal() {
        return markerIdReal;
    }

    public void setMarkerIdReal(String markerIdReal) {
        this.markerIdReal = markerIdReal;
    }
    public static TaskDetailDto Convert(TaskDetail taskDetail)
    {
        if (taskDetail == null) {
            return null;
        }
        TaskDetailDto taskDetailDto=new TaskDetailDto();
        taskDetailDto.setId(taskDetail.getId());
        taskDetailDto.setIsChecked(taskDetail.getIsChecked());
        taskDetailDto.setIsNormal(taskDetail.getIsNormal());
        taskDetailDto.setImagePath(taskDetail.getImagePath());
        taskDetailDto.setDescription(taskDetail.getDescription());
        taskDetailDto.setMarkerIdReal(taskDetail.getMarkerIdReal());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (taskDetail.getFinishTime() != null)
            taskDetailDto.setFinishTime(sdf.format(taskDetail.getFinishTime()));

        Task task = taskDetail.getTask();
        if(task!=null)
        {
            taskDetailDto.setTaskId(task.getId() + "");
            taskDetailDto.setTaskCode(task.getTaskCode());
        }
        taskDetailDto.setMarkerName(taskDetail.getMarkerName());
        taskDetailDto.setMarkerId(taskDetail.getMarkerId());
        taskDetailDto.setLatitude(taskDetail.getLatitude());
        taskDetailDto.setLongitude(taskDetail.getLongitude());
        return taskDetailDto;
    }



    public static List<TaskDetailDto> Converts(List<TaskDetail> taskDetails)
    {
        List<TaskDetailDto> taskDetailDtos= new ArrayList<TaskDetailDto>();
        for (TaskDetail taskDetail : taskDetails)
        {
            taskDetailDtos.add(TaskDetailDto.Convert(taskDetail));
        }
        return taskDetailDtos;
    }
}
