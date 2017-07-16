package com.casic.accessControl.marker.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lenovo on 2017/6/13.
 */
@Entity
@Table(name = "repair_point")
public class RepairPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String markerId;
    private String leftThickness;
    private String taskNo;
    private String reason;
    private String repairMethod;
    private String groundType;
    private Date repairTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getLeftThickness() {
        return leftThickness;
    }

    public void setLeftThickness(String leftThickness) {
        this.leftThickness = leftThickness;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRepairMethod() {
        return repairMethod;
    }

    public void setRepairMethod(String repairMethod) {
        this.repairMethod = repairMethod;
    }

    public String getGroundType() {
        return groundType;
    }

    public void setGroundType(String groundType) {
        this.groundType = groundType;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }
}
