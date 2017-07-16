package com.casic.accessControl.task.dto;

import java.util.Map;

/**
 * Created by lenovo on 2016/5/30.
 */
public class TaskStatus {
    private String status;
    private Long statusCount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Long statusCount) {
        this.statusCount = statusCount;
    }

}
