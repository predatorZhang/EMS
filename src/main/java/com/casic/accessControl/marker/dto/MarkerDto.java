package com.casic.accessControl.marker.dto;

import com.casic.accessControl.core.util.StringUtils;
import com.casic.accessControl.marker.domain.Marker;
import com.casic.accessControl.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/5/16.
 */
public class MarkerDto {
    private Long id;
    private String markerObjectType;

/*
    @Size(min = 1, max = 30, message = "{items.name.length.error}")
*/
    @NotNull(message="{items.createtime.isNull}")
    @Email(message="{items.name.length.error}")
    private String ownerComp;//权属单位

    private String area;//所属区域
    private String antiCorrodedNo;//防腐工号
    private String projectName;//工程名称
    private String projectNo;//工程编号
    private String line;//管网编号

    private String pipeLength;//管线长度
    private String jobNo;//作业编号;
    private String jobContent;//作业内容
    private String pressLevel;//压力级制
    private String pipeMaterial;
    private String pipeDiameter;
    private String depth;//设计埋深
    private String antiCorrodedType;//防腐层种类
    private String designThickness;//设计壁厚
    private String road;//所属道路
    private String testPile;
    private String devCode;//设备编码
    private String testPileBuildComp;//测试桩建设单位
    private String constructTime;//建设年代
    private String x_cord;
    private String y_cord;
    private String memo;//备注
    private String markerId;
    private String markerType;
    private String markerDepth;//标识器埋深
    private String department;
    private String creator;//安装人员
    private String createTime;
    private Double longitude;//经度
    private Double latitude;//纬度
    private Integer recordType;//记录类型，1:管线，2:管线附属物,3:管线特征点,4:交叉穿越点
    private Integer isValid;
    private Long companyId;
    private String ext1;
    private String ext2;

    private String newLineNo;
    private String newPipeMaterial;
    private String newPipeDiameter;
    private String newAntiCorrodedType;//防腐层种类
    private String newDesignThickness;//设计壁厚
    private String newConstructTime;//设计壁厚

    private String sDate;
    private String eDate;

//    private String btnEdit = "<a href='#' class='btn mini blue'><i class='icon-edit'></i>编辑</a>";
//    private String btnDelete = "<a href='#' class='btn mini red'><i class='icon-trash'></i>删除</a>";

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

    public String getX_cord() {
        return x_cord;
    }

    public void setX_cord(String x_cord) {
        this.x_cord = x_cord;
    }

    public String getY_cord() {
        return y_cord;
    }

    public void setY_cord(String y_cord) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
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

    public static MarkerDto convert2MarkerDto(Marker marker) {
        if (marker == null) return null;
        MarkerDto result = new MarkerDto();
        result.setId(marker.getId());
        result.setMarkerObjectType(doNullChange(marker.getMarkerObjectType()));
        result.setOwnerComp(doNullChange(marker.getOwnerComp()));
        result.setArea(doNullChange(marker.getArea()));
        result.setAntiCorrodedNo(doNullChange(marker.getAntiCorrodedNo()));
        result.setProjectName(doNullChange(marker.getProjectName()));
        result.setProjectNo(doNullChange(marker.getProjectNo()));
        result.setLine(doNullChange(marker.getLine()));
        result.setPipeLength(doNullChange(marker.getPipeLength()));
        result.setJobNo(doNullChange(marker.getJobNo()));
        result.setJobContent(doNullChange(marker.getJobContent()));
        result.setPressLevel(doNullChange(marker.getPressLevel()));
        result.setPipeMaterial(doNullChange(marker.getPipeMaterial()));
        result.setPipeDiameter(doNullChange(marker.getPipeDiameter()));
        result.setDepth(doNullChange(marker.getDepth()));
        result.setAntiCorrodedType(doNullChange(marker.getAntiCorrodedType()));
        result.setDesignThickness(doNullChange(marker.getDesignThickness()));
        result.setRoad(doNullChange(marker.getRoad()));
        result.setTestPile(doNullChange(marker.getTestPile()));
        result.setDevCode(doNullChange(marker.getDevCode()));
        result.setTestPileBuildComp(doNullChange(marker.getTestPileBuildComp()));
        result.setConstructTime(doNullChange(marker.getConstructTime()));
        result.setX_cord(doNullChange(marker.getX_cord()));
        result.setY_cord(doNullChange(marker.getY_cord()));
        result.setMemo(doNullChange(marker.getMemo()));

        result.setMarkerId(marker.getMarkerId());
        result.setMarkerType(doNullChange(marker.getMarkerType()));
        result.setMarkerDepth(doNullChange(marker.getMarkerDepth()));
        result.setDepartment(doNullChange(marker.getDepartment()));
        result.setCreator(marker.getCreator());
        result.setCreateTime(marker.getCreateTime().toString().substring(0, 19));
        result.setLatitude(marker.getLatitude());
        result.setLongitude(marker.getLongitude());
        result.setIsValid(marker.getIsValid());
        result.setCompanyId(marker.getCompanyId());
        result.setExt1(doNullChange(marker.getExt1()));
        result.setExt2(doNullChange(marker.getExt2()));
        result.setRecordType(marker.getRecordType());

        result.setNewAntiCorrodedType(doNullChange(marker.getNewAntiCorrodedType()));
        result.setNewConstructTime(doNullChange(marker.getNewConstructTime()));
        result.setNewDesignThickness(doNullChange(marker.getNewDesignThickness()));
        result.setNewLineNo(doNullChange(marker.getNewLineNo()));
        result.setNewPipeDiameter(doNullChange(marker.getNewPipeDiameter()));
        result.setNewPipeMaterial(doNullChange(marker.getNewPipeMaterial()));
        return result;
    }

    public static Marker convert2Marker(MarkerDto markerDto) {
        if (markerDto == null) return null;
        Marker result = new Marker();
        if (markerDto.getId() != null) {
            result.setId(markerDto.getId());
        }
        result.setMarkerObjectType(markerDto.getMarkerObjectType());
        result.setOwnerComp(markerDto.getOwnerComp());
        result.setArea(markerDto.getArea());
        result.setAntiCorrodedNo(markerDto.getAntiCorrodedNo());
        result.setProjectName(markerDto.getProjectName());
        result.setProjectNo(markerDto.getProjectNo());
        result.setLine(markerDto.getLine());
        result.setPipeLength(markerDto.getPipeLength());
        result.setJobNo(markerDto.getJobNo());
        result.setJobContent(markerDto.getJobContent());
        result.setPressLevel(markerDto.getPressLevel());
        result.setPipeMaterial(markerDto.getPipeMaterial());
        result.setPipeDiameter(markerDto.getPipeDiameter());
        result.setDepth(markerDto.getDepth());
        result.setAntiCorrodedType(markerDto.getAntiCorrodedType());
        result.setDesignThickness(markerDto.getDesignThickness());
        result.setRoad(markerDto.getRoad());
        result.setTestPile(markerDto.getTestPile());
        result.setDevCode(markerDto.getDevCode());
        result.setTestPileBuildComp(markerDto.getTestPileBuildComp());
        result.setConstructTime(markerDto.getConstructTime());
        if(StringUtils.isNotBlank(markerDto.getX_cord()))result.setX_cord(Double.valueOf(markerDto.getX_cord()));
        if(StringUtils.isNotBlank(markerDto.getY_cord()))result.setY_cord(Double.valueOf(markerDto.getY_cord()));
        result.setMemo(markerDto.getMemo());

        result.setMarkerId(markerDto.getMarkerId());
        result.setMarkerType(doNullChange(markerDto.getMarkerType()));
        result.setMarkerDepth(doNullChange(markerDto.getMarkerDepth()));
        result.setDepartment(doNullChange(markerDto.getDepartment()));
        result.setCreator(markerDto.getCreator());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            if (StringUtils.isNotBlank(markerDto.getCreateTime())) {
                result.setCreateTime(sdf.parse(markerDto.getCreateTime()));
            } else {
                result.setCreateTime(new Date());
            }
        } catch (ParseException e) {
            try {
                result.setCreateTime(DateUtils.sdf2.parse(markerDto.getCreateTime()));
            } catch (ParseException e1) {
                result.setCreateTime(new Date());
            }
        }
        result.setLatitude(markerDto.getLatitude());
        result.setLongitude(markerDto.getLongitude());
        result.setCompanyId(markerDto.getCompanyId());
        result.setExt1(markerDto.getExt1());
        result.setExt2(markerDto.getExt2());
        result.setRecordType(markerDto.getRecordType());
        result.setNewAntiCorrodedType(markerDto.getNewAntiCorrodedType());
        result.setNewConstructTime(markerDto.getNewConstructTime());
        result.setNewDesignThickness(markerDto.getNewDesignThickness());
        result.setNewLineNo(markerDto.getNewLineNo());
        result.setNewPipeDiameter(markerDto.getNewPipeDiameter());
        result.setNewPipeMaterial(markerDto.getNewPipeMaterial());

        return result;
    }

    private static String doNullChange(Object obj) {
        return obj==null?"":String.valueOf(obj);
    }

    public static List<MarkerDto> convert2MarkerDtoList(List<Marker> markers) {
        if (CollectionUtils.isEmpty(markers)) {
            return Collections.emptyList();
        }
        List<MarkerDto> res = new ArrayList<MarkerDto>();
        for (Marker marker : markers) {
            res.add(convert2MarkerDto(marker));
        }
        return res;
    }

}
