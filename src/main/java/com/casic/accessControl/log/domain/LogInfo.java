package com.casic.accessControl.log.domain;


import com.casic.accessControl.user.domain.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lenovo on 2016/5/16.
 */
//日志信息
@Entity
@Table(name = "loginfo")
public class LogInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "operation")
    private String operation;
    @Column(name = "logTime")
    private Date logTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}
