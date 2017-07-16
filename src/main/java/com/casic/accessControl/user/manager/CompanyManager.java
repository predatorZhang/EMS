package com.casic.accessControl.user.manager;

import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.user.domain.Company;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2016/4/20.
 */
@Service("companyManager")
public class CompanyManager extends HibernateEntityDao<Company> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取用户角色
     *
     * @param
     * @return
     */
    @Cacheable(value = "getCompanies")
    public List<Company> getCompanies() {

        Criteria criteria = this.createCriteria(Company.class);
        criteria.add(Restrictions.eq("isValid", 1));
        List<Company> companies = criteria.list();
        return companies;
    }

    public Company getCompanyById(Long companyId){
        Company company = null;
       company = this.get(companyId);
        return company;
    }

}
