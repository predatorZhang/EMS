package com.casic.accessControl.user.dto;

import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.Role;
import com.casic.accessControl.user.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/20.
 */
public class RoleDto {

    private Long id;
    private String roleName;
    private String functions;
    private Integer isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public static RoleDto Convert(Role role)
    {
        if (role == null) {
            return null;
        }
        RoleDto roleDto=new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setIsValid(role.getIsValid());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setFunctions(role.getFunctions());
        return roleDto;
    }
    public static List<RoleDto> Converts(List<Role> roles)
    {
        List<RoleDto> roleDtos= new ArrayList<RoleDto>();
        for (Role role : roles)
        {
            roleDtos.add(RoleDto.Convert(role));
        }
        return roleDtos;
    }
}
