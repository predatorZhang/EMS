package com.casic.accessControl.task.domain;

import com.casic.accessControl.user.domain.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lenovo on 2016/5/16.
 */
//人员位置信息
@Entity
@Table(name = "position")
public class Position {
    private Long id;
    private double longitude;
    private double latitude;
    private Date localTime;
    private User patroler;
    private Task task;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "localTime")
    public Date getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Date localTime) {
        this.localTime = localTime;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "patroler")
    public User getPatroler() {
        return patroler;
    }

    public void setPatroler(User patroler) {
        this.patroler = patroler;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "task")
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
