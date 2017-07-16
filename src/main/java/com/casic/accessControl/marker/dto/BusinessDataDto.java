package com.casic.accessControl.marker.dto;


import com.casic.accessControl.core.util.StringUtils;
import com.casic.accessControl.marker.domain.BusinessData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/3/29.
 */
public class BusinessDataDto {
    private Long id;

    private String protectivePotential;//保护电位
    private String openCircuitPotential;//开路电位
    private String emissionCurrent;//发射电流
    private String refEleCalibration;//参比电极校准
    private String memo;
    private String markerId;
    private String maintain;
    private String status;
    private String checker;
    private String checkTime;
    private String testPile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProtectivePotential() {
        return protectivePotential;
    }

    public void setProtectivePotential(String protectivePotential) {
        this.protectivePotential = protectivePotential;
    }

    public String getOpenCircuitPotential() {
        return openCircuitPotential;
    }

    public void setOpenCircuitPotential(String openCircuitPotential) {
        this.openCircuitPotential = openCircuitPotential;
    }

    public String getEmissionCurrent() {
        return emissionCurrent;
    }

    public void setEmissionCurrent(String emissionCurrent) {
        this.emissionCurrent = emissionCurrent;
    }
    public String getRefEleCalibration() {
        return refEleCalibration;
    }

    public void setRefEleCalibration(String refEleCalibration) {
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

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }

    public String getTestPile() {
        return testPile;
    }

    public void setTestPile(String testPile) {
        this.testPile = testPile;
    }

    public static BusinessDataDto convert2BusinessDto(BusinessData businessData){
        BusinessDataDto businessDataDto = new BusinessDataDto();
        if(businessData.getRefEleCalibration()!=null) businessDataDto.setRefEleCalibration(String.valueOf(businessData.getRefEleCalibration()));
        else  businessDataDto.setRefEleCalibration("");
        if(businessData.getProtectivePotential()!=null)  businessDataDto.setProtectivePotential(String.valueOf(businessData.getProtectivePotential()));
        else  businessDataDto.setProtectivePotential("");
        if(businessData.getOpenCircuitPotential()!=null) businessDataDto.setOpenCircuitPotential(String.valueOf(businessData.getOpenCircuitPotential()));
        else  businessDataDto.setOpenCircuitPotential("");
        businessDataDto.setId(businessData.getId());
        businessDataDto.setMarkerId(businessData.getMarkerId());
        if(businessData.getEmissionCurrent()!=null) businessDataDto.setEmissionCurrent(String.valueOf(businessData.getEmissionCurrent()));
        else businessDataDto.setEmissionCurrent("");
        businessDataDto.setMemo(businessData.getMemo());
        businessDataDto.setMaintain(businessData.getMaintain());
        businessDataDto.setStatus(businessData.getStatus());
        businessDataDto.setChecker(businessData.getChecker());
        if(businessData.getCheckTime()!=null)businessDataDto.setCheckTime(businessData.getCheckTime().toString().substring(0, 19));
        else businessDataDto.setCheckTime("");
        return businessDataDto;
    }
    public static BusinessData convert2BusinessData(BusinessDataDto businessDataDto){
        BusinessData businessData = new BusinessData();
        if(StringUtils.isNotBlank(businessDataDto.getEmissionCurrent()))businessData.setEmissionCurrent(Double.valueOf(businessDataDto.getEmissionCurrent()));
        businessData.setMemo(businessDataDto.getMemo());
        businessData.setMarkerId(businessDataDto.getMarkerId());
        if(StringUtils.isNotBlank(businessDataDto.getOpenCircuitPotential()))businessData.setOpenCircuitPotential(Double.valueOf(businessDataDto.getOpenCircuitPotential()));
        if(StringUtils.isNotBlank(businessDataDto.getProtectivePotential()))businessData.setProtectivePotential(Double.valueOf(businessDataDto.getProtectivePotential()));
        if(StringUtils.isNotBlank(businessDataDto.getRefEleCalibration())) businessData.setRefEleCalibration(Double.valueOf(businessDataDto.getRefEleCalibration()));
        businessData.setMaintain(businessDataDto.getMaintain());
        businessData.setStatus(businessDataDto.getStatus());
        businessData.setChecker(businessDataDto.getChecker());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            if (StringUtils.isNotBlank(businessDataDto.getCheckTime())) {
                businessData.setCheckTime(sdf.parse(businessDataDto.getCheckTime()));
            } else {
                businessData.setCheckTime(new Date());
            }
        } catch (ParseException e) {
            businessData.setCheckTime(new Date());
        }
        return businessData;
    }

    public static List<BusinessDataDto> convert2BusinessDtos(List<BusinessData> businessDatas){
        List<BusinessDataDto> businessDataDtos = new ArrayList<>(businessDatas.size());
        for(BusinessData businessData:businessDatas)  {
            businessDataDtos.add(convert2BusinessDto(businessData));

        }
        return businessDataDtos;
    }
}
