package com.casic.accessControl.marker.dto;

import com.casic.accessControl.core.util.StringUtils;

/**
 * Created by lenovo on 2017/3/30.
 */
public class MarkerBusinessDto{

    private MarkerDto markerDto;
    private BusinessDataDto businessDataDto;
    private RepairPointDto repairPointDto;

    public MarkerDto getMarkerDto() {
        return markerDto;
    }

    public void setMarkerDto(MarkerDto markerDto) {
        this.markerDto = markerDto;
    }

    public BusinessDataDto getBusinessDataDto() {
        return businessDataDto;
    }
    public void setBusinessDataDto(BusinessDataDto businessDataDto) {
        this.businessDataDto = businessDataDto;
    }

    public RepairPointDto getRepairPointDto() {
        return repairPointDto;
    }

    public void setRepairPointDto(RepairPointDto repairPointDto) {
        this.repairPointDto = repairPointDto;
    }
}
