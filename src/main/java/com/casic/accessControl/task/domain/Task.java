package com.casic.accessControl.task.domain;

import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**工单信息
 * Created by lenovo on 2016/5/16.
 */
//工单
@Entity
@Table(name = "task")
public class Task {
    private Long id;
    private String taskCode;
    private String description;
    private Date deployDate;//布置时间
    private Date beginDate;//实际开始时间
    private Date endDate;//实际结束时间
    private int status;//工单状态，0：未开始，1：正在执行，2:已完成
    private User creator;//下发人员
    private User patroler;//巡检人员
    private List<TaskDetail> taskDetails;
    private Integer isValid;
    private Company company;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "taskCode")
    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "deployDate")
    public Date getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(Date deployDate) {
        this.deployDate = deployDate;
    }

    @Column(name = "beginDate")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Column(name = "endDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "creator")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "patroler")
    public User getPatroler() {
        return patroler;
    }

    public void setPatroler(User patroler) {
        this.patroler = patroler;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    public List<TaskDetail> getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(List<TaskDetail> taskDetails) {
        this.taskDetails = taskDetails;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "company",referencedColumnName = "id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
