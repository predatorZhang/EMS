package com.casic.accessControl.task.dto;

import com.casic.accessControl.task.domain.Task;
import com.casic.accessControl.user.domain.User;
import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo on 2016/4/13.
 */
public class TaskDto {
    private Long id;
    private String taskCode;
    private String description;
    private String deployDate;//布置时间
    private String beginDate;//实际开始时间
    private String endDate;//实际结束时间
    private String status;//工单状态，0：未开始，1：正在执行，2:已完成
    private String creatorId;//下发人员
    private String creatorName;//下发人员
    private String patrolerId;//
    private String patrolerName;//
    private Integer isValid;

    private String btnEdit = "<a href='#' class='btn mini blue'><i class='icon-edit'></i>查看</a>";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(String deployDate) {
        this.deployDate = deployDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public static TaskDto Convert(Task task) {
        if (task == null) {
            return null;
        }
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTaskCode(task.getTaskCode());
        taskDto.setDescription(task.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (task.getDeployDate() != null)
            taskDto.setDeployDate(sdf.format(task.getDeployDate()));
        if (task.getBeginDate() != null)
            taskDto.setBeginDate(sdf.format(task.getBeginDate()));
        if (task.getEndDate() != null)
            taskDto.setEndDate(sdf.format(task.getEndDate()));
        taskDto.setIsValid(task.getIsValid());

        if (0 == task.getStatus()) {
            taskDto.setStatus("未开始");
        } else if (1 == task.getStatus()) {
            taskDto.setStatus("正在执行");
        } else if (2 == task.getStatus()) {
            taskDto.setStatus("完成");
        }
        User creator = task.getCreator();
        if (creator != null) {
            taskDto.setCreatorId(creator.getId() + "");
            taskDto.setCreatorName(creator.getUserName());
        }
        User patroler = task.getPatroler();
        if (patroler != null) {
            taskDto.setPatrolerId(patroler.getId() + "");
            taskDto.setPatrolerName(patroler.getUserName());
        }
        return taskDto;
    }

    public static List<TaskDto> Converts(List<Task> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            return Collections.emptyList();
        }
        List<TaskDto> taskDtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            taskDtos.add(TaskDto.Convert(task));
        }
        return taskDtos;
    }
}
