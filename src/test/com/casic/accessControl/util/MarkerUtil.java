package com.casic.accessControl.util;

/**
 * Created by lenovo on 2016/8/31.
 */
public class MarkerUtil {
    private Long id;
    private String markerObjectType;
    private String pipeMaterial;
    private String pipeDiameter;
    private String layStyle;//埋设方式
    private String depth;//埋深
    private String belowType;//下层管种类
    private String belowDepth;//下层管埋深
    private String belowDiameter;//下层管直径
    private String belowMaterial;//下层管材料
    private String road;//所属道路
    private String constructTime;//建设年代
    private String ownerComp;//权属单位
    private String markerObjectId;
    private String markerId;
    private String markerType;
    private String creator;
    private String markerDepth;//标识器埋深
    private Double longitude;//经度
    private Double latitude;//纬度
    private String createTime;
    private String memo;//备注
    private Integer recordType;//记录类型，1:管线，2:管线附属物,3:管线特征点,4:交叉穿越点
    private Integer isValid;
    private Long companyId;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarkerObjectType() {
        return markerObjectType;
    }

    public void setMarkerObjectType(String markerObjectType) {
        this.markerObjectType = markerObjectType;
    }

    public String getPipeMaterial() {
        return pipeMaterial;
    }

    public void setPipeMaterial(String pipeMaterial) {
        this.pipeMaterial = pipeMaterial;
    }

    public String getPipeDiameter() {
        return pipeDiameter;
    }

    public void setPipeDiameter(String pipeDiameter) {
        this.pipeDiameter = pipeDiameter;
    }

    public String getLayStyle() {
        return layStyle;
    }

    public void setLayStyle(String layStyle) {
        this.layStyle = layStyle;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getBelowType() {
        return belowType;
    }

    public void setBelowType(String belowType) {
        this.belowType = belowType;
    }

    public String getBelowDepth() {
        return belowDepth;
    }

    public void setBelowDepth(String belowDepth) {
        this.belowDepth = belowDepth;
    }

    public String getBelowDiameter() {
        return belowDiameter;
    }

    public void setBelowDiameter(String belowDiameter) {
        this.belowDiameter = belowDiameter;
    }

    public String getBelowMaterial() {
        return belowMaterial;
    }

    public void setBelowMaterial(String belowMaterial) {
        this.belowMaterial = belowMaterial;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getConstructTime() {
        return constructTime;
    }

    public void setConstructTime(String constructTime) {
        this.constructTime = constructTime;
    }

    public String getOwnerComp() {
        return ownerComp;
    }

    public void setOwnerComp(String ownerComp) {
        this.ownerComp = ownerComp;
    }

    public String getMarkerObjectId() {
        return markerObjectId;
    }

    public void setMarkerObjectId(String markerObjectId) {
        this.markerObjectId = markerObjectId;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMarkerDepth() {
        return markerDepth;
    }

    public void setMarkerDepth(String markerDepth) {
        this.markerDepth = markerDepth;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
