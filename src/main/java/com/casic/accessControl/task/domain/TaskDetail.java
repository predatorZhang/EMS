package com.casic.accessControl.task.domain;

import javax.persistence.*;
import java.util.Date;

/**工单任务信息
 * Created by lenovo on 2016/5/16.
 */
@Entity
@Table(name = "taskDetail")
public class TaskDetail {
    private Long id;
    private Task task;
    private String markerName;//关联点的信息
    private Long markerId;
    private Double longitude;//经度
    private Double latitude;//纬度
    private Integer isChecked;//是否巡检完成
    private Integer isNormal;
    private String imagePath;
    private String description;
    private Date finishTime;
    private String markerIdReal;//物理上的标签ID，在移动端需要使用

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "task")
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Column(name = "markerName")
    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    @Column(name="markerIdReal")
    public String getMarkerIdReal(){
        return markerIdReal;
    }
    public void setMarkerIdReal(String markerIdReal){
        this.markerIdReal = markerIdReal;
    }
    @Column(name = "markerId")
    public Long getMarkerId() {
        return markerId;
    }

    public void setMarkerId(Long markerId) {
        this.markerId = markerId;
    }

    @Column(name = "isChecked")
    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    @Column(name = "isNormal")
    public Integer getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Integer isNormal) {
        this.isNormal = isNormal;
    }

    @Column(name = "imagePath")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "finishTime")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
