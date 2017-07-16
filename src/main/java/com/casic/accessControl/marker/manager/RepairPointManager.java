package com.casic.accessControl.marker.manager;

import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.core.util.StringUtils;
import com.casic.accessControl.marker.domain.RepairPoint;
import com.casic.accessControl.marker.dto.MarkerDto;
import com.casic.accessControl.marker.dto.RepairPointDto;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/6/13.
 */
@Service
public class RepairPointManager extends HibernateEntityDao<RepairPoint>{
    public Map<String,RepairPointDto> getLatestRepairePointMap(List<MarkerDto> markerDtos){
        Map<String,RepairPointDto> res = new HashMap<String,RepairPointDto>();
        StringBuilder markerIds=new StringBuilder();
        for(MarkerDto markerDto:markerDtos){
            if(markerDto!=null&& StringUtils.isNotBlank(markerDto.getMarkerId())){
                markerIds.append("'"+markerDto.getMarkerId()+"',");
            }
        }
        String condition = "";
        String markerIdStrs =  markerIds.toString();//.substring(0,markerIds.toString().length()-1);
        if(markerIdStrs.length()>1){
            markerIdStrs = markerIdStrs.substring(0,markerIdStrs.length()-1);
            condition = " and markerId in("+markerIdStrs+")";
        }
        String hql = "from RepairPoint where id in (select max(id) from RepairPoint where 1=1 "+condition+" group by markerId)";
        Query query = this.getSession().createQuery(hql);
        List<RepairPoint> repairPoints =  query.list();
        for(RepairPoint repairPoint:repairPoints){
            res.put(repairPoint.getMarkerId(),RepairPointDto.convert2RepairPointDto(repairPoint));
        }
        return res;
    }
    public boolean saveRepairData(RepairPointDto repairPointDto) {
        if (repairPointDto == null) {
            return false;
        }
        RepairPoint businessData = RepairPointDto.convert2RepairPoint(repairPointDto);
        this.save(businessData);
        return true;
    }
}
