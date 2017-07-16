package com.casic.accessControl.user.manager;

import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.user.domain.Role;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo on 2016/4/20.
 */
@Service("roleManager")
public class RoleManager extends HibernateEntityDao<Role> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取用户角色
     *
     * @param
     * @return
     */
    @Cacheable(value = "getRoles")
    public List<Role> getRoles() {

        Criteria criteria = this.createCriteria(Role.class);
        criteria.add(Restrictions.eq("isValid", 1));
        List<Role> roles = criteria.list();
        if (roles == null || roles.size() == 0) {
            return Collections.emptyList();
        }
        return roles;
    }

    @Cacheable(value = "getRoleByType")
    public Role getRoleByType(Integer type) {
        Criteria criteria = this.createCriteria(Role.class);
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.eq("isValid", 1));
        List<Role> roles = criteria.list();
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        } else {
            return roles.get(0);
        }
    }
}
