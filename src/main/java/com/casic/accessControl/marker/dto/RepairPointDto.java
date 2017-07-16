package com.casic.accessControl.marker.dto;

import com.casic.accessControl.marker.domain.RepairPoint;
import com.casic.accessControl.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2017/6/13.
 */
public class RepairPointDto {
    private Long id;
    private String markerId;
    private String leftThickness;
    private String taskNo;
    private String reason;
    private String repairMethod;
    private String groundType;
    private String repairTime;

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

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public static RepairPointDto convert2RepairPointDto(RepairPoint repairPoint){
      RepairPointDto repairPointDto = new RepairPointDto();
        repairPointDto.setId(repairPoint.getId());
        repairPointDto.setGroundType(repairPoint.getGroundType());
        repairPointDto.setLeftThickness(repairPoint.getLeftThickness());
        repairPointDto.setReason(repairPoint.getReason());
        repairPointDto.setMarkerId(repairPoint.getMarkerId());
        repairPointDto.setRepairMethod(repairPoint.getRepairMethod());
        repairPointDto.setTaskNo(repairPoint.getTaskNo());
        repairPointDto.setRepairTime(repairPoint.getRepairTime().toString().substring(0,19));
        return repairPointDto;
    }
    public static RepairPoint convert2RepairPoint(RepairPointDto repairPointDto){
        RepairPoint repairPoint = new RepairPoint();
        repairPoint.setId(repairPointDto.getId());
        repairPoint.setGroundType(repairPointDto.getGroundType());
        repairPoint.setLeftThickness(repairPointDto.getLeftThickness());
        repairPoint.setReason(repairPointDto.getReason());
        repairPoint.setMarkerId(repairPointDto.getMarkerId());
        repairPoint.setRepairMethod(repairPointDto.getRepairMethod());
        repairPoint.setTaskNo(repairPointDto.getTaskNo());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            if (StringUtils.isNotBlank(repairPointDto.getRepairTime())) {
                repairPoint.setRepairTime(sdf.parse(repairPointDto.getRepairTime()));
            } else {
                repairPoint.setRepairTime(new Date());
            }
        } catch (ParseException e) {
            repairPoint.setRepairTime(new Date());
        }
        return repairPoint;
    }
}
