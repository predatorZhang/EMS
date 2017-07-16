package com.casic.accessControl.marker.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lenovo on 2017/3/29.
 */
@Entity
@Table(name = "businessdata")
public class BusinessData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "protectivePotential")
    private Double protectivePotential;//保护电位
    @Column(name = "openCircuitPotential")
    private Double openCircuitPotential;//开路电位
    @Column(name = "emissionCurrent")
    private Double emissionCurrent;//发射电流
    @Column(name = "refEleCalibration")
    private Double refEleCalibration;//参比电极校准
    @Column(name = "memo")
    private String memo;
    @Column(name = "markerId")
    private String markerId;
    @Column(name="maintain")//维护处理
    private String maintain;
    @Column(name="status")
    private String status;
    @Column(name="checker")
    private String checker;
    @Column(name="checkTime")
    private Date checkTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProtectivePotential() {
        return protectivePotential;
    }

    public void setProtectivePotential(Double protectivePotential) {
        this.protectivePotential = protectivePotential;
    }

    public Double getOpenCircuitPotential() {
        return openCircuitPotential;
    }

    public void setOpenCircuitPotential(Double openCircuitPotential) {
        this.openCircuitPotential = openCircuitPotential;
    }

    public Double getEmissionCurrent() {
        return emissionCurrent;
    }

    public void setEmissionCurrent(Double emissionCurrent) {
        this.emissionCurrent = emissionCurrent;
    }

    public Double getRefEleCalibration() {
        return refEleCalibration;
    }

    public void setRefEleCalibration(Double refEleCalibration) {
        this.refEleCalibration = refEleCalibration;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }
}
