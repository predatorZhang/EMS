package com.casic.accessControl.marker.domain;


import javax.persistence.*;
import java.util.Date;

/**
 * 标识器信息
 * Created by lenovo on 2016/5/16.
 */
@Entity
@Table(name = "marker")
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "markerObjectType")
    private String markerObjectType;
    @Column(name = "ownerComp")
    private String ownerComp;//权属单位
    @Column(name = "area")
    private String area;//所属区域
    @Column(name = "antiCorrodedNo")
    private String antiCorrodedNo;//防腐工号
    @Column(name = "projectName")
    private String projectName;//工程名称
    @Column(name = "projectNo")
    private String projectNo;//工程编号
    @Column(name = "line")
    private String line;//管网编号
    @Column(name = "pipeLength")
    private String pipeLength;//管线长度
    @Column(name = "jobNo")
    private String jobNo;//作业编号;
    @Column(name = "jobContent")
    private String jobContent;//作业内容
    @Column(name = "pressLevel")
    private String pressLevel;//压力级制
    @Column(name="pipeMaterial")
    private String pipeMaterial;
    @Column(name="pipeDiameter")
    private String pipeDiameter;
    @Column(name = "depth")
    private String depth;//设计埋深
    @Column(name = "antiCorrodedType")
    private String antiCorrodedType;//防腐层种类
    @Column(name = "designThickness")
    private String designThickness;//设计壁厚
    @Column(name="road")
    private String road;//所属道路
    @Column(name = "testPile")
    private String testPile;
    @Column(name = "devCode")
   private String devCode;//设备编码
    @Column(name="testPileBuildComp")
   private String testPileBuildComp;//测试桩建设单位
    @Column(name="construct_time")
    private String constructTime;//建设年代

    @Column(name="newLineNo")
    private String newLineNo;
    @Column(name="newPipeMaterial")
    private String newPipeMaterial;
    @Column(name="newPipeDiameter")
    private String newPipeDiameter;
    @Column(name = "newAntiCorrodedType")
    private String newAntiCorrodedType;//防腐层种类
    @Column(name = "newDesignThickness")
    private String newDesignThickness;//设计壁厚
    @Column(name = "new_construct_time")
    private String newConstructTime;//设计壁厚

    @Column(name = "x_cord")
    private Double x_cord;
    @Column(name = "y_cord")
    private Double y_cord;
    @Column(name = "memo")
    private String memo;//备注
    @Column(name = "markerId")
    private String markerId;
    @Column(name = "markerType")
    private String markerType;
    @Column(name = "marker_depth")
    private String markerDepth;//标识器埋深
    @Column(name="department")
    private String department;
    @Column(name="creator")
    private String creator;//安装人员
    @Column(name = "createTime")
    private Date createTime;
    @Column(name = "longitude")
    private Double longitude;//经度
    @Column(name = "latitude")
    private Double latitude;//纬度
    @Column(name="record_type")
    private Integer recordType;//记录类型，1:管线，2:管线附属物,3:管线特征点,4:交叉穿越点
    @Column(name = "isValid")
    private Integer isValid;
    @Column(name = "company_id")
    private Long companyId;
    @Column(name = "ext1")
    private String ext1;
    @Column(name = "ext2")
    private String ext2;

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

    public String getOwnerComp() {
        return ownerComp;
    }

    public void setOwnerComp(String ownerComp) {
        this.ownerComp = ownerComp;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAntiCorrodedNo() {
        return antiCorrodedNo;
    }

    public void setAntiCorrodedNo(String antiCorrodedNo) {
        this.antiCorrodedNo = antiCorrodedNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPipeLength() {
        return pipeLength;
    }

    public void setPipeLength(String pipeLength) {
        this.pipeLength = pipeLength;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getPressLevel() {
        return pressLevel;
    }

    public void setPressLevel(String pressLevel) {
        this.pressLevel = pressLevel;
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

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getAntiCorrodedType() {
        return antiCorrodedType;
    }

    public void setAntiCorrodedType(String antiCorrodedType) {
        this.antiCorrodedType = antiCorrodedType;
    }

    public String getDesignThickness() {
        return designThickness;
    }

    public void setDesignThickness(String designThickness) {
        this.designThickness = designThickness;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getTestPile() {
        return testPile;
    }

    public void setTestPile(String testPile) {
        this.testPile = testPile;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getTestPileBuildComp() {
        return testPileBuildComp;
    }

    public void setTestPileBuildComp(String testPileBuildComp) {
        this.testPileBuildComp = testPileBuildComp;
    }

    public String getConstructTime() {
        return constructTime;
    }

    public void setConstructTime(String constructTime) {
        this.constructTime = constructTime;
    }

    public Double getX_cord() {
        return x_cord;
    }

    public void setX_cord(Double x_cord) {
        this.x_cord = x_cord;
    }

    public Double getY_cord() {
        return y_cord;
    }

    public void setY_cord(Double y_cord) {
        this.y_cord = y_cord;
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

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public String getMarkerDepth() {
        return markerDepth;
    }

    public void setMarkerDepth(String markerDepth) {
        this.markerDepth = markerDepth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getNewLineNo() {
        return newLineNo;
    }

    public void setNewLineNo(String newLineNo) {
        this.newLineNo = newLineNo;
    }

    public String getNewPipeMaterial() {
        return newPipeMaterial;
    }

    public void setNewPipeMaterial(String newPipeMaterial) {
        this.newPipeMaterial = newPipeMaterial;
    }

    public String getNewPipeDiameter() {
        return newPipeDiameter;
    }

    public void setNewPipeDiameter(String newPipeDiameter) {
        this.newPipeDiameter = newPipeDiameter;
    }

    public String getNewAntiCorrodedType() {
        return newAntiCorrodedType;
    }

    public void setNewAntiCorrodedType(String newAntiCorrodedType) {
        this.newAntiCorrodedType = newAntiCorrodedType;
    }

    public String getNewDesignThickness() {
        return newDesignThickness;
    }

    public void setNewDesignThickness(String newDesignThickness) {
        this.newDesignThickness = newDesignThickness;
    }

    public String getNewConstructTime() {
        return newConstructTime;
    }

    public void setNewConstructTime(String newConstructTime) {
        this.newConstructTime = newConstructTime;
    }
}
