package com.casic.accessControl.user.domain;

import javax.persistence.*;

/**角色信息
 * Created by lenovo on 2016/5/16.
 */
@Entity
@Table(name = "role")
public class Role{
    private Long id;
    private String roleName;
    private String functions;
    private Integer isValid;
    private Integer type;//新增，用来表示是否为管理员，可细分，0表示管理员，其他暂不考虑

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "roleName")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "functions")
    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    @Column(name = "isValid")
    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }


    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
