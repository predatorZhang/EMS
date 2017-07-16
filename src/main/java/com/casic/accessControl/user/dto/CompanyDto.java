package com.casic.accessControl.user.dto;

import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.Role;
import com.casic.accessControl.user.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/20.
 */
public class CompanyDto {

    private Long id;
    private String companyName;
    private String phone;
    private Integer isValid;

    private String btnEdit = "<a href='#' class='btn mini blue'><i class='icon-edit'></i>编辑</a>";
    private String btnDelete = "<a href='#' class='btn mini red'><i class='icon-trash'></i>删除</a>";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public static CompanyDto Convert(Company company)
    {
        if (company == null) {
            return null;
        }
        CompanyDto companyDto=new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setIsValid(company.getIsValid());
        companyDto.setPhone(company.getPhone());
        companyDto.setCompanyName(company.getCompanyName());
        return companyDto;
    }
    public static List<CompanyDto> Converts(List<Company> companies)
    {
        List<CompanyDto> companyDtos= new ArrayList<CompanyDto>();
        for (Company company : companies)
        {
            companyDtos.add(CompanyDto.Convert(company));
        }
        return companyDtos;
    }
}
