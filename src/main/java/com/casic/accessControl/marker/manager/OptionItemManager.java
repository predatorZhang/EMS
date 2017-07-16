package com.casic.accessControl.marker.manager;

import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.core.util.StringUtils;
import com.casic.accessControl.marker.domain.OptionItem;
import com.casic.accessControl.marker.dto.OptionItemDto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2016/12/6.
 */
@Service
public class OptionItemManager extends HibernateEntityDao<OptionItem> {

    public List<OptionItemDto> getItemsList(Long companyId){
        Criteria criteria = this.getSession().createCriteria(OptionItem.class);
        criteria.add(Restrictions.or(Restrictions.eq("cid",0L),Restrictions.eq("cid",companyId)));
        List<OptionItem> optionItemList = criteria.list();
        return OptionItemDto.convert2OptionItemDtoList(optionItemList);
    }

    public boolean isExistItem(Long companyId,String itemName,String type){
        Criteria criteria = this.getSession().createCriteria(OptionItem.class);
        criteria.add(Restrictions.or(Restrictions.eq("cid",0L),Restrictions.eq("cid",companyId)));
        criteria.add(Restrictions.eq("name",itemName)).add(Restrictions.eq("type",type));
        List<OptionItem> optionItemList = criteria.list();
        if(optionItemList.size()>0) return true;
        return false;
    }
//如果不存在就插入，以后优化为批量的
    public void saveIfNotExists(Long companyId,String itemName,String type){
        if(StringUtils.isBlank(itemName)) return;
        if(!isExistItem(companyId,itemName,type)){
            OptionItem item = new OptionItem();
            item.setType(type);
            item.setName(itemName);
            item.setCid(companyId);
            save(item);
        }
    }
}
