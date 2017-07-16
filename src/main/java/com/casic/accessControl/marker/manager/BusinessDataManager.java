package com.casic.accessControl.marker.manager;

import com.casic.accessControl.core.ext.export.CsvExportor;
import com.casic.accessControl.core.ext.export.TableModel;
import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.core.page.Page;
import com.casic.accessControl.core.util.StringUtils;
import com.casic.accessControl.marker.domain.BusinessData;
import com.casic.accessControl.marker.dto.BusinessDataDto;
import com.casic.accessControl.marker.dto.MarkerDto;
import com.casic.accessControl.util.DataTable;
import com.casic.accessControl.util.DataTableParameter;
import com.casic.accessControl.util.DataTableUtils;
import com.casic.accessControl.util.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/29.
 */
@Service
public class BusinessDataManager extends HibernateEntityDao<BusinessData> {
    @Resource
    private CsvExportor exportor;
    @Resource
    private MarkerManager markerManager;

    public DataTable<BusinessDataDto> getBusinessDataTable(String params,String beginDate,String endDate,String testPile) throws ParseException {
        DataTable<BusinessDataDto> result = new DataTable<BusinessDataDto>();
        DataTableParameter parameter = DataTableUtils.getDataTableParameterByJsonParam(params);
        int start = parameter.getiDisplayStart();
        int pageSize = parameter.getiDisplayLength();
        int pageNo = (start / pageSize) + 1;
        String [] markerIdArray =null;
        Map<String,String> markerIdTestPileMap = new HashMap<String,String>();
        if(StringUtils.isNotBlank(testPile)){//限制阴保桩条件
            String markerIds = "";
            List<MarkerDto>  markerDtos = markerManager.getMarkerByTestPile(testPile);
            for(MarkerDto markerDto:markerDtos) {
                if(StringUtils.isEmpty(markerDto.getMarkerId())) continue;
                markerIds+=markerDto.getMarkerId()+",";
                markerIdTestPileMap.put(markerDto.getMarkerId(),StringUtils.isEmpty(markerDto.getTestPile())?"--":markerDto.getTestPile());
            }
            if(markerIds.length()>1) markerIdArray = markerIds.substring(0,markerIds.length()-1).split(",");
            else{
                result.setAaData(Collections.EMPTY_LIST);
                result.setiTotalDisplayRecords(0);
                result.setiTotalRecords(0);
                result.setsEcho(parameter.getsEcho());
                return result;
            }
        }
        Criteria criteria = this.getSession().createCriteria(BusinessData.class);
        if(StringUtils.isNotBlank(beginDate)){
            criteria.add(Restrictions.ge("checkTime", DateUtils.sdf1.parse(beginDate)));
        }
        if(StringUtils.isNotBlank(endDate)){
            criteria.add(Restrictions.le("checkTime",DateUtils.sdf2.parse(endDate+" 23:59:59")));
        }
        if(markerIdArray!=null&&markerIdArray.length!=0){
            criteria.add(Restrictions.in("markerId",markerIdArray));
        }
        criteria.addOrder(Order.desc("checkTime"));
        Page page = pagedQuery(criteria, pageNo, pageSize);
        List<BusinessDataDto> businessDataDtos = BusinessDataDto.convert2BusinessDtos((List<BusinessData>) page.getResult());
        if(StringUtils.isBlank(testPile)){//查询所有的阴保桩，需要根据markerId将阴保桩信息查询出来，然后再赋值返回
            for(BusinessDataDto businessDataDto:businessDataDtos){
                List<MarkerDto> markerDtos = markerManager.getMarkerByMarkerId(businessDataDto.getMarkerId());
                if(markerDtos.size()==0) businessDataDto.setTestPile("");
                else businessDataDto.setTestPile(markerDtos.get(0).getTestPile());
            }
        }else{
            for(BusinessDataDto businessDataDto:businessDataDtos){
                businessDataDto.setTestPile(markerIdTestPileMap.get(businessDataDto.getMarkerId()));
            }
        }
        result.setAaData(businessDataDtos);
        result.setiTotalDisplayRecords((int) page.getTotalCount());
        result.setiTotalRecords((int) page.getTotalCount());
        result.setsEcho(parameter.getsEcho());
        return result;
    }

    public List<BusinessDataDto> getBusinessDataDtos(String beginDate,String endDate,String testPile) throws ParseException {
        String [] markerIdArray =null;
        if(StringUtils.isNotBlank(testPile)){//限制阴保桩条件
            String markerIds = "";
            List<MarkerDto>  markerDtos = markerManager.getMarkerByTestPile(testPile);
            for(MarkerDto markerDto:markerDtos) markerIds+=markerDto.getMarkerId()+",";
            markerIdArray = markerIds.substring(0,markerIds.length()-1).split(",");
        }
        Criteria criteria = this.getSession().createCriteria(BusinessData.class);
        if(StringUtils.isNotBlank(beginDate)){
            criteria.add(Restrictions.ge("checkTime", DateUtils.sdf1.parse(beginDate)));
        }
        if(StringUtils.isNotBlank(endDate)){
            criteria.add(Restrictions.le("checkTime",DateUtils.sdf1.parse(endDate)));
        }
        if(markerIdArray!=null&&markerIdArray.length!=0){
            criteria.add(Restrictions.in("markerId",markerIdArray));
        }
        criteria.addOrder(Order.desc("checkTime"));
        List<BusinessDataDto> businessDataDtos = BusinessDataDto.convert2BusinessDtos(criteria.list());
        if(StringUtils.isBlank(testPile)){//查询所有的阴保桩，需要根据markerId将阴保桩信息查询出来，然后再赋值返回
            for(BusinessDataDto businessDataDto:businessDataDtos){
                List<MarkerDto> markerDtos = markerManager.getMarkerByMarkerId(businessDataDto.getMarkerId());
                if(markerDtos.size()==0) businessDataDto.setTestPile("");
                else businessDataDto.setTestPile(markerDtos.get(0).getTestPile());
            }
        }else{
            for(BusinessDataDto businessDataDto:businessDataDtos){
                businessDataDto.setTestPile(testPile);
            }
        }
        return businessDataDtos;
    }

    public boolean saveBusinessData(BusinessDataDto businessDataDto) {
        if (businessDataDto == null) {
            return false;
        }
        BusinessData businessData = BusinessDataDto.convert2BusinessData(businessDataDto);
        this.save(businessData);
        return true;
    }

    /***
     * 获取最新业务数据的map,<markerId,businessData></>
     * @return
     */
    public Map<String,BusinessDataDto> getLatestBusinessDataMap(List<MarkerDto> markerDtos){
        Map<String,BusinessDataDto> res = new HashMap<String,BusinessDataDto>();
        StringBuilder markerIds=new StringBuilder();
        for(MarkerDto markerDto:markerDtos){
            if(markerDto!=null&&StringUtils.isNotBlank(markerDto.getMarkerId())){
                markerIds.append("'"+markerDto.getMarkerId()+"',");
            }
        }
        String condition = "";
        String markerIdStrs =  markerIds.toString();//.substring(0,markerIds.toString().length()-1);
        if(markerIdStrs.length()>1){
            markerIdStrs = markerIdStrs.substring(0,markerIdStrs.length()-1);
            condition = " and markerId in("+markerIdStrs+")";
        }
        String hql = "from BusinessData where id in (select max(id) from BusinessData where 1=1 "+condition+" group by markerId)";
        Query query = this.getSession().createQuery(hql);
        List<BusinessData> businessDatas =  query.list();
        for(BusinessData businessData:businessDatas){
            res.put(businessData.getMarkerId(),BusinessDataDto.convert2BusinessDto(businessData));
        }
        return res;
    }

    public void exportExcel(String beginDate,String endDate,String testPile,HttpServletRequest request, HttpServletResponse response) throws ParseException {
        List<BusinessDataDto> businessDataDtos = getBusinessDataDtos(beginDate,endDate,testPile);
        String excelName = "阴保桩电位信息";
        String excelColumns = "testPile,checkTime,protectivePotential,openCircuitPotential,emissionCurrent,refEleCalibration";
        String alias ="阴保桩名称,采集时间,保护电位(-V),开路电位(-V),发射电流(mA),参比电极校准(mA)";
        String[] columns = excelColumns.split(",");
        String[] headerAlias = alias.split(",");
        String userAgent = request.getHeader("user-agent");
        if (userAgent == null || (userAgent.indexOf("Firefox") < 0 && userAgent.indexOf("Chrome") < 0&& userAgent.indexOf("Safari") < 0)) {
            try {
                excelName= URLEncoder.encode(excelName, "UTF8"); //其他浏览器
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        TableModel tableModel = new TableModel();
        tableModel.setName(excelName);
        tableModel.addHeaders(columns);
        tableModel.addHeaderAlias(headerAlias);
        tableModel.setData(businessDataDtos);
        response.setCharacterEncoding("UTF-8");
        try {
            exportor.export(request, response, tableModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
