package com.casic.accessControl.marker.manager;

import com.casic.accessControl.core.ext.export.CsvExportor;
import com.casic.accessControl.core.ext.export.TableModel;
import com.casic.accessControl.core.hibernate.HibernateEntityDao;
import com.casic.accessControl.core.page.Page;
import com.casic.accessControl.core.util.StringUtils;
import com.casic.accessControl.marker.dto.BusinessDataDto;
import com.casic.accessControl.marker.dto.MarkerBusinessDto;
import com.casic.accessControl.marker.dto.MarkerDto;
import com.casic.accessControl.marker.domain.Marker;
import com.casic.accessControl.marker.dto.RepairPointDto;
import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.User;
import com.casic.accessControl.util.DataTable;
import com.casic.accessControl.util.DataTableParameter;
import com.casic.accessControl.util.DataTableUtils;
import com.casic.accessControl.util.DateUtils;
import com.google.gson.Gson;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

/**
 * Created by lenovo on 2016/5/16.
 */
@Service
public class MarkerManager extends HibernateEntityDao<Marker> {


    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CsvExportor exportor;
    @Resource
    private BusinessDataManager businessDataManager;
    @Resource
    private RepairPointManager repairPointManager;

    public DataTable<MarkerDto> pageQueryMarker(String params, String markerDto, Long companyId) {
        DataTable<MarkerDto> result = new DataTable<MarkerDto>();
        DataTableParameter parameter = DataTableUtils.getDataTableParameterByJsonParam(params);
        int start = parameter.getiDisplayStart();
        int pageSize = parameter.getiDisplayLength();
        int pageNo = (start / pageSize) + 1;
        Criteria criteria1 = this.createCriteria(Marker.class);

        if (null != markerDto) {
            MarkerDto markerDto1 = new Gson().fromJson(markerDto, MarkerDto.class);

            if (markerDto1.getRecordType() != null) {
                criteria1.add(Restrictions.eq("recordType", markerDto1.getRecordType()));
            }
            if (StringUtils.isNotBlank(markerDto1.getMarkerId())) {
                criteria1.add(Restrictions.like("markerId", markerDto1.getMarkerId(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getMemo())) {
                criteria1.add(Restrictions.like("memo", markerDto1.getMemo(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getRoad())) {
                criteria1.add(Restrictions.like("road", markerDto1.getRoad(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getOwnerComp())&&!markerDto1.getOwnerComp().equals("全部")) {
                criteria1.add(Restrictions.like("ownerComp", markerDto1.getOwnerComp(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getDepartment())) {
                criteria1.add(Restrictions.like("department", markerDto1.getDepartment(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getArea())&&!markerDto1.getArea().equals("全部")) {
                criteria1.add(Restrictions.like("area", markerDto1.getArea(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getLine())) {
                criteria1.add(Restrictions.like("line", markerDto1.getLine(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getPressLevel())&&!markerDto1.getPressLevel().equals("全部")) {
                criteria1.add(Restrictions.like("pressLevel", markerDto1.getPressLevel(), MatchMode.EXACT));
            }
            if (StringUtils.isNotBlank(markerDto1.getDevCode())) {
                criteria1.add(Restrictions.like("devCode", markerDto1.getDevCode(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getTestPile())) {
                criteria1.add(Restrictions.like("testPile", markerDto1.getTestPile(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getY_cord())) {
                criteria1.add(Restrictions.like("y_cord", markerDto1.getY_cord(), MatchMode.EXACT));
            }
            if (StringUtils.isNotBlank(markerDto1.getX_cord())) {
                criteria1.add(Restrictions.like("x_cord", markerDto1.getX_cord(), MatchMode.EXACT));
            }
            if (StringUtils.isNotBlank(markerDto1.getDepartment())) {
                criteria1.add(Restrictions.like("department", markerDto1.getDepartment(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getMarkerObjectType())) {
                criteria1.add(Restrictions.like("markerObjectType", markerDto1.getMarkerObjectType(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(markerDto1.getsDate())) {
                try {
                    criteria1.add(Restrictions.ge("createTime", DateUtils.sdf1.parse(markerDto1.getsDate())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(markerDto1.geteDate())) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtils.sdf1.parse(markerDto1.geteDate()));
                    calendar.add(Calendar.DATE, 1);
                    criteria1.add(Restrictions.le("createTime", calendar.getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        criteria1.add(Restrictions.eq("companyId", companyId));
        criteria1.add(Restrictions.eq("isValid", 1));
        criteria1.addOrder(Order.desc("createTime"));
        Page page = pagedQuery(criteria1, pageNo, pageSize);
        List<MarkerDto> markerInfos = MarkerDto.convert2MarkerDtoList((List<Marker>) page.getResult());
        result.setAaData(markerInfos);
        result.setiTotalDisplayRecords((int) page.getTotalCount());
        result.setiTotalRecords((int) page.getTotalCount());
        result.setsEcho(parameter.getsEcho());
        return result;
    }

    /**
     *
     * @param id
     * @return
     */
    public MarkerDto getMarkerById(Long id) {
        if (id == null) {
            logger.info("ERROR,MarkerService->getMarkerById,Id is null");
            return null;
        }
        Criteria criteria = this.createCriteria(Marker.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.eq("isValid", 1));
        Marker marker = (Marker) criteria.uniqueResult();
        return MarkerDto.convert2MarkerDto(marker);
    }

    //获取marker
    public List<Marker> getMarkerByIds(String ids) {
        try {
            ids = ids.trim();
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
            String[] markerIds = ids.split(",");
            Long[] markerIds_long = new Long[markerIds.length];
            for (int i = 0; i < markerIds.length; i++) {
                markerIds_long[i] = Long.parseLong(markerIds[i]);
            }
            Criteria criteria = this.createCriteria(Marker.class);
            criteria.add(Restrictions.in("id", markerIds_long));
            criteria.add(Restrictions.eq("isValid", 1));
            List<Marker> markers = criteria.list();
            return markers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<MarkerDto> getMarkerByMarkerId(String markerId) {
        if (markerId == null||StringUtils.isBlank(markerId)) {
            logger.info("ERROR,MarkerService->getMarkerByMarkerId,markerId is null");
            return Collections.emptyList();
        }
        Criteria criteria = this.createCriteria(Marker.class);
        criteria.add(Restrictions.eq("markerId", markerId));
        criteria.add(Restrictions.eq("isValid", 1));
        List<Marker> markers =criteria.list();
        return MarkerDto.convert2MarkerDtoList(markers);
    }

    public List<MarkerDto> getMarkerByTestPile(String testPile) {
        if (testPile == null||StringUtils.isBlank(testPile)) {
            logger.info("ERROR,MarkerService->getMarkerByMarkerId,markerId is null");
            return  Collections.emptyList();
        }
        Criteria criteria = this.createCriteria(Marker.class);
        criteria.add(Restrictions.like("testPile", testPile,MatchMode.ANYWHERE));
        criteria.add(Restrictions.eq("isValid", 1));
        List<Marker> markers =criteria.list();
        return MarkerDto.convert2MarkerDtoList(markers);
    }

    public boolean saveMarker(Marker marker) {
        try {
            if (marker == null) {
                logger.error("MakerService ->saveMarker ERROR,marker is null!");
                return false;
            }
            this.getSession().saveOrUpdate(marker);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 新增标识器 8-9
     *
     * @param markers
     * @param companyId
     * @return
     */
    public boolean batchSave(List<MarkerDto> markers, Long companyId) {
        if (CollectionUtils.isEmpty(markers) || companyId == null) {
            return false;
        }
        for (MarkerDto markerDto : markers) {
            Marker marker = MarkerDto.convert2Marker(markerDto);
            marker.setCompanyId(companyId);
            marker.setIsValid(1);
            this.saveMarker(marker);
            //插入下拉列表的值
//            int type = markerDto.getRecordType();
//            optionItemManager.saveIfNotExists(companyId,markerDto.getMarkerObjectType(), "markerType");
//            optionItemManager.saveIfNotExists(companyId,markerDto.getArea(),"areaItem");
//            optionItemManager.saveIfNotExists(companyId,markerDto.getPipeMaterial(),"MaterialType");
        }
        return true;

    }

    //改为软删除
    public void delMarkerByIds(String ids) {
        if (StringUtils.isBlank(ids) || ids.indexOf(")") != -1) {//未传入值，直接返回，防止无效查询,过滤非法请求，防止sql注入
            return;
        }
        try {
            ids = ids.trim();
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
            Query query = this.getSession().createQuery("update Marker as f set f.isValid = 0 where f.id in (" + ids + ")");
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据条件查询标识器的ID列表
     *
     * @return
     */
    public List<MarkerBusinessDto> getMarkerBusinessDtos(MarkerDto condition,Long companyId) {
        if (companyId == null) {
            return Collections.emptyList();
        }
        Criteria criteria = this.getSession().createCriteria(Marker.class);
        criteria.add(Restrictions.eq("isValid", 1)).add(Restrictions.eq("companyId", companyId));
        if(condition!=null)
        {
            if(condition.getRecordType()!=null){
                criteria.add(Restrictions.eq("recordType", condition.getRecordType()));
            }
            if (StringUtils.isNotBlank(condition.getMarkerObjectType())) {
                criteria.add(Restrictions.like("markerObjectType", condition.getMarkerObjectType(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(condition.getOwnerComp())&&!condition.getOwnerComp().equals("全部")) {
                criteria.add(Restrictions.like("ownerComp", condition.getOwnerComp(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(condition.getArea())&&!condition.getArea().equals("全部")) {
                criteria.add(Restrictions.like("area", condition.getArea(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(condition.getPressLevel())&&!condition.getPressLevel().equals("全部")) {
                criteria.add(Restrictions.eq("pressLevel", condition.getPressLevel()));
            }
            if (StringUtils.isNotBlank(condition.getDevCode())) {
                criteria.add(Restrictions.like("devCode", condition.getDevCode(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(condition.getTestPile())) {
                criteria.add(Restrictions.like("testPile", condition.getTestPile(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(condition.getY_cord())) {
                criteria.add(Restrictions.eq("y_cord", condition.getY_cord()));
            }
            if (StringUtils.isNotBlank(condition.getX_cord())) {
                criteria.add(Restrictions.eq("x_cord", condition.getX_cord()));
            }
            if (StringUtils.isNotBlank(condition.getLine())) {
                criteria.add(Restrictions.like("line", condition.getLine(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(condition.getMemo())) {
                criteria.add(Restrictions.like("memo", condition.getMemo(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(condition.getRoad())) {
                criteria.add(Restrictions.like("road", condition.getRoad(), MatchMode.ANYWHERE));
            }

            if (StringUtils.isNotBlank(condition.getDepartment())) {
                criteria.add(Restrictions.like("department", condition.getDepartment(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(condition.getsDate())) {
                try {
                    criteria.add(Restrictions.ge("createTime", DateUtils.sdf1.parse(condition.getsDate())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(condition.geteDate())) {
                try {
                    criteria.add(Restrictions.le("createTime", DateUtils.sdf2.parse(condition.geteDate() + " 23:59:59")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        List<Marker> markers = criteria.list();
        List<MarkerDto> markerDtos = MarkerDto.convert2MarkerDtoList(markers);
        return convertMarkerBusinessDtos(markerDtos);
    }

    /**
     * 将MarkerDto列表转换为MarkerBusinessDto列表，以便适应新需求
     * @param markerDtos
     * @return
     */
    public List<MarkerBusinessDto> convertMarkerBusinessDtos(List<MarkerDto> markerDtos){
        if(markerDtos==null||markerDtos.size()==0) return Collections.emptyList();
        List<MarkerBusinessDto> res = new ArrayList<MarkerBusinessDto>(markerDtos.size());
        Map<String,BusinessDataDto> businessDataDtoMap = businessDataManager.getLatestBusinessDataMap(markerDtos);//获取最新的业务数据
        Map<String,RepairPointDto> repairPointDtoMap = repairPointManager.getLatestRepairePointMap(markerDtos);
        for(MarkerDto markerDto:markerDtos){
            MarkerBusinessDto markerBusinessDto = new MarkerBusinessDto();
            markerBusinessDto.setMarkerDto(markerDto);
            markerBusinessDto.setBusinessDataDto(businessDataDtoMap.get(markerDto.getMarkerId()));
            markerBusinessDto.setRepairPointDto(repairPointDtoMap.get(markerDto.getMarkerId()));
            res.add(markerBusinessDto);
        }
        return res;
    }

    /**
     * @param inputStream   文件输入流
     * @param userCompanyId 当前登录用户公司ID
     * @return 成功返回0，Excel表不含内容返回1，读取Excel异常返回4  (用户所属公司不匹配返回2，Excel表中ID不正确返回3,将Excel表中的ID删除了)
     */
    public int readExcel(InputStream inputStream, Long userCompanyId,int recordTypeFlag) {
        try {
            Workbook book = Workbook.getWorkbook(inputStream);
            Sheet sheet = book.getSheet(0);
            int rows = sheet.getRows();
            if (rows < 2) {//Excel中没有数据
                return 1;
            }
            int columns = sheet.getColumns();
            Map<String, Integer> indexMap = new HashMap<String, Integer>();
            List<MarkerDto> markerDtos = new ArrayList<MarkerDto>();
            for (int i = 0; i < columns; i++) {//解析出来excel的头，找出对应位置,以适应excel文件头部顺序的变化
                String name = sheet.getCell(i, 0).getContents();
                switch (name) {
                    case "特征管点":
                        indexMap.put("markerObjectType", i);
                        break;
                    case "附属物名称":
                        indexMap.put("markerObjectType", i);
                        break;
                    case "权属单位":
                        indexMap.put("ownerComp", i);
                        break;
                    case "所属区域":
                        indexMap.put("area",i);
                        break;
                    case "防腐工号":
                        indexMap.put("antiCorrodedNo",i);
                        break;
                    case "工程名称":
                        indexMap.put("projectName",i);
                        break;
                    case "工程编号":
                        indexMap.put("projectNo",i);
                        break;
                    case "管网编码":
                        indexMap.put("line",i);
                        break;
                    case "作业编号":
                        indexMap.put("jobNo",i);
                        break;
                    case "作业内容":
                        indexMap.put("jobContent",i);
                        break;
                    case "管线长度":
                        indexMap.put("pipeLength",i);
                        break;
                    case "压力级制":
                        indexMap.put("pressLevel",i);
                        break;
                    case "管径":
                        indexMap.put("pipeDiameter", i);
                        break;
                    case "材质":
                        indexMap.put("pipeMaterial", i);
                        break;
                    case "设计埋深":
                        indexMap.put("depth", i);
                        break;
                    case "防腐层种类":
                        indexMap.put("antiCorrodedType", i);
                        break;
                    case "设计壁厚":
                        indexMap.put("designThickness", i);
                        break;
                    case "测试桩名称":
                        indexMap.put("testPile", i);
                        break;
                    case "设备编码":
                        indexMap.put("devCode", i);
                        break;
                    case "测试桩建设单位":
                        indexMap.put("testPileBuildComp", i);
                        break;
                    case "建设时间":
                        indexMap.put("constructTime", i);
                        break;
                    case "X坐标":
                        indexMap.put("x_cord", i);
                        break;
                    case "Y坐标":
                        indexMap.put("y_cord", i);
                        break;
                    case "备注":
                        indexMap.put("memo", i);
                        break;
                    case "标识器ID":
                        indexMap.put("markerId", i);
                        break;
                    case "类型":
                        indexMap.put("markerType", i);
                        break;
                    case "埋深":
                        indexMap.put("markerDepth", i);
                        break;
                    case "部门":
                        indexMap.put("creator", i);
                        break;
                    case "时间":
                        indexMap.put("createTime", i);
                        break;
                    case "经度":
                        indexMap.put("longitude", i);
                        break;
                    case "纬度":
                        indexMap.put("latitude", i);
                        break;
                    case "新管网编码":
                        indexMap.put("newLineNo", i);
                        break;
                    case "新管径":
                        indexMap.put("newPipeDiameter", i);
                        break;
                    case "新管材":
                        indexMap.put("newPipeMaterial", i);
                        break;
                    case "新管壁厚度":
                        indexMap.put("newDesignThickness", i);
                        break;
                    case "新防腐层种类":
                        indexMap.put("newAntiCorrodedType", i);
                        break;
                    case "新建设时间":
                        indexMap.put("newConstructTime", i);
                        break;

                }

            }
           /*  DecimalFormat decimalFormatter = new DecimalFormat("###.#############");*/
            for (int i = 1; i < rows; i++) {
                //TODO LIST:写一个判断excel行内容全部为空的方法
                if(isEmptyRow(sheet.getRow(i))) continue;
                MarkerDto markerDto = new MarkerDto();
                if (recordTypeFlag == 1||recordTypeFlag==3) {//管线位置or特征管点
                    markerDto.setAntiCorrodedNo(sheet.getCell(indexMap.get("antiCorrodedNo"), i).getContents());
                    markerDto.setProjectName(sheet.getCell(indexMap.get("projectName"), i).getContents());
                    markerDto.setProjectNo(sheet.getCell(indexMap.get("projectNo"), i).getContents());
                    markerDto.setPipeDiameter(sheet.getCell(indexMap.get("pipeDiameter"), i).getContents());
                    markerDto.setPipeMaterial(sheet.getCell(indexMap.get("pipeMaterial"), i).getContents());
                    markerDto.setDepth(sheet.getCell(indexMap.get("depth"), i).getContents());
                    markerDto.setDesignThickness(sheet.getCell(indexMap.get("designThickness"), i).getContents());
                    markerDto.setRoad(sheet.getCell(indexMap.get("road"), i).getContents());
                    markerDto.setConstructTime(sheet.getCell(indexMap.get("constructTime"), i).getContents());
                    markerDto.setRoad(sheet.getCell(indexMap.get("road"), i).getContents());
                } else if (recordTypeFlag == 2) {//阴保桩
                    markerDto.setRoad(sheet.getCell(indexMap.get("road"), i).getContents());
                    markerDto.setDevCode(sheet.getCell(indexMap.get("devCode"), i).getContents());
                    markerDto.setTestPileBuildComp(sheet.getCell(indexMap.get("testPileBuildComp"), i).getContents());
                }else if(recordTypeFlag==4){//抢修位置
                    markerDto.setPipeLength(sheet.getCell(indexMap.get("pipeLength"), i).getContents());
                    markerDto.setPipeDiameter(sheet.getCell(indexMap.get("pipeDiameter"), i).getContents());
                    markerDto.setConstructTime(sheet.getCell(indexMap.get("constructTime"), i).getContents());
                }else{//接切线位置
                    markerDto.setJobNo(sheet.getCell(indexMap.get("jobNo"), i).getContents());
                    markerDto.setJobContent(sheet.getCell(indexMap.get("jobContent"), i).getContents());
                    markerDto.setNewAntiCorrodedType(sheet.getCell(indexMap.get("newAntiCorrodedType"), i).getContents());
                    markerDto.setNewConstructTime(sheet.getCell(indexMap.get("newConstructTime"), i).getContents());
                    markerDto.setNewPipeMaterial(sheet.getCell(indexMap.get("newPipeMaterial"), i).getContents());
                    markerDto.setNewPipeDiameter(sheet.getCell(indexMap.get("newPipeDiameter"), i).getContents());
                    markerDto.setNewLineNo(sheet.getCell(indexMap.get("newLineNo"), i).getContents());
                    markerDto.setNewDesignThickness(sheet.getCell(indexMap.get("newDesignThickness"), i).getContents());
                }
                markerDto.setMarkerObjectType(sheet.getCell(indexMap.get("markerObjectType"), i).getContents());
                markerDto.setOwnerComp(sheet.getCell(indexMap.get("ownerComp"), i).getContents());
                markerDto.setArea(sheet.getCell(indexMap.get("area"), i).getContents());
                markerDto.setLine(sheet.getCell(indexMap.get("line"), i).getContents());
                markerDto.setPressLevel(sheet.getCell(indexMap.get("pressLevel"), i).getContents());
                markerDto.setAntiCorrodedType(sheet.getCell(indexMap.get("antiCorrodedType"), i).getContents());
                markerDto.setX_cord(sheet.getCell(indexMap.get("x_cord"), i).getContents());
                markerDto.setY_cord(sheet.getCell(indexMap.get("y_cord"), i).getContents());
                markerDto.setMemo(sheet.getCell(indexMap.get("memo"), i).getContents());

                markerDto.setMarkerId(sheet.getCell(indexMap.get("markerId"), i).getContents());
                markerDto.setMarkerType(sheet.getCell(indexMap.get("markerType"), i).getContents());
                markerDto.setMarkerDepth(sheet.getCell(indexMap.get("markerDepth"), i).getContents());
                Cell loCell1 = sheet.getCell(indexMap.get("longitude"), i);
                Cell latCell1 =sheet.getCell(indexMap.get("latitude"), i);
                if(loCell1.getType()!= CellType.NUMBER||latCell1.getType()!=CellType.NUMBER){
                    //经纬度坐标格式不符合要求
                    return 3;
                }
                markerDto.setLongitude(((NumberCell)loCell1).getValue());
                markerDto.setLatitude(((NumberCell) latCell1).getValue());
                markerDto.setIsValid(1);
                markerDto.setRecordType(recordTypeFlag);
                markerDto.setCreateTime(sheet.getCell(indexMap.get("createTime"), i).getContents());
                markerDto.setDepartment(sheet.getCell(indexMap.get("department"), i).getContents());
                String creator = sheet.getCell(indexMap.get("creator"), i).getContents();
               if(StringUtils.isNotBlank(creator)) markerDto.setCreator(creator);
                markerDtos.add(markerDto);
            }
            book.close();
            batchSave(markerDtos, userCompanyId);
        } catch (BiffException e) {
            logger.error(e.getMessage());
            return 4;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return 4;
        }catch(Exception e){
            logger.error(e.getMessage());
            return 4;
        }
        return 0;
    }

    private boolean isEmptyRow(Cell []cells){
        for(Cell cell:cells){
            if(StringUtils.isBlank(cell.getContents()))continue;
            return false;
        }
        return true;
    }

    /**
     *
     * @param companyId 操作用户的公司ID
     * @param outPath  输出路径
     * @return
     */
    //TODO LIST：wxl 待更新
    public boolean exportServerExcel(Long companyId,String outPath){
        List<MarkerBusinessDto> markerDtos = null;
        markerDtos = getMarkerBusinessDtos(null,companyId);
        try {
            //打开文件
            WritableWorkbook book = Workbook.createWorkbook(new File(outPath));
            WritableSheet sheet = book.createSheet("第一页", 0);
            String [] titleTxt ={"标识对象类别","管线种类","权属单位","所属区域","防腐工号","工程名称","工程编号","管线编号","管线长度",
                    "作业编号","作业内容","压力等级","管材","管径","设计埋深","防腐层种类","设计壁厚","所属道路",
                    "测试桩名称", "设备编码","测试桩建设单位","建设年代", "X坐标","Y坐标", "备注",
                    "标识器ID","标识器类型","标识器埋深","安装部门","安装时间","经度","纬度","新管编号","新管管材","新管管径","新管防腐层种类","新管壁厚","新管建设时间",
                    "保护电位","开路电位","发射电流","参比电极校准","测试桩状态","维护处理","剩余壁厚","抢修任务编号","漏气原因","修复方式","地面类型","修复时间"};
            Label[][] labels = new Label[markerDtos.size() + 1][titleTxt.length];
            for(int k=0;k<titleTxt.length;k++){
                labels[0][k] = new Label(k,0,titleTxt[k]);
            }
            for (int i = 0; i < markerDtos.size(); i++) {
                MarkerBusinessDto markerDto = markerDtos.get(i);
                String protectivePotential="",openCircuitPotential="",emissionCurrent="",refEleCalibration="",status = "",maintain ="";
                String xCord = markerDto.getMarkerDto().getX_cord()==null||markerDto.getMarkerDto().getX_cord().equals("null")?"":markerDto.getMarkerDto().getX_cord().toString();
                String yCord = markerDto.getMarkerDto().getY_cord()==null||markerDto.getMarkerDto().getY_cord().equals("null")?"":markerDto.getMarkerDto().getY_cord().toString();
                String latitude = markerDto.getMarkerDto().getLatitude()==null?"":markerDto.getMarkerDto().getLatitude().toString();
                String longtitude = markerDto.getMarkerDto().getLongitude()==null?"":markerDto.getMarkerDto().getLongitude().toString();
                if(markerDto.getBusinessDataDto()!=null){//有业务数据的处理
                     protectivePotential = markerDto.getBusinessDataDto().getProtectivePotential()==null?"":markerDto.getBusinessDataDto().getProtectivePotential().toString();
                    openCircuitPotential = markerDto.getBusinessDataDto().getOpenCircuitPotential()==null?"":markerDto.getBusinessDataDto().getOpenCircuitPotential().toString();
                    emissionCurrent = markerDto.getBusinessDataDto().getEmissionCurrent()==null?"":markerDto.getBusinessDataDto().getEmissionCurrent().toString();
                    refEleCalibration = markerDto.getBusinessDataDto().getRefEleCalibration()==null?"":markerDto.getBusinessDataDto().getRefEleCalibration().toString();
                    maintain = markerDto.getBusinessDataDto().getMaintain();
                    status = markerDto.getBusinessDataDto().getStatus();
                }
                String leftThickness ="",taskNo ="",reason="",repairMethod ="",groundType = "",repairTime ="";
                if(markerDto.getRepairPointDto()!=null){
                    leftThickness = markerDto.getRepairPointDto().getLeftThickness();
                    taskNo = markerDto.getRepairPointDto().getTaskNo();
                    reason = markerDto.getRepairPointDto().getReason();
                    repairMethod =  markerDto.getRepairPointDto().getRepairMethod();
                    groundType =markerDto.getRepairPointDto().getGroundType();
                    repairTime = markerDto.getRepairPointDto().getRepairTime();
                }
                String[] content = {String.valueOf(markerDto.getMarkerDto().getRecordType()), markerDto.getMarkerDto().getMarkerObjectType(), markerDto.getMarkerDto().getOwnerComp(),
                        markerDto.getMarkerDto().getArea(), markerDto.getMarkerDto().getAntiCorrodedNo(),markerDto.getMarkerDto().getProjectName(),markerDto.getMarkerDto().getProjectNo(),
                        markerDto.getMarkerDto().getLine(), markerDto.getMarkerDto().getPipeLength(), markerDto.getMarkerDto().getJobNo(), markerDto.getMarkerDto().getJobContent(),
                        markerDto.getMarkerDto().getPressLevel(), markerDto.getMarkerDto().getPipeMaterial(),markerDto.getMarkerDto().getPipeDiameter(),markerDto.getMarkerDto().getDepth(),
                        markerDto.getMarkerDto().getAntiCorrodedType(), markerDto.getMarkerDto().getDesignThickness(), markerDto.getMarkerDto().getRoad(), markerDto.getMarkerDto().getTestPile(),
                        markerDto.getMarkerDto().getDevCode(),markerDto.getMarkerDto().getTestPileBuildComp(),markerDto.getMarkerDto().getConstructTime(),xCord,yCord,
                        markerDto.getMarkerDto().getMemo(),markerDto.getMarkerDto().getMarkerId(),markerDto.getMarkerDto().getMarkerType(),markerDto.getMarkerDto().getMarkerDepth(),
                        String.valueOf(markerDto.getMarkerDto().getCreator()),markerDto.getMarkerDto().getCreateTime(),longtitude,latitude,markerDto.getMarkerDto().getNewLineNo(),markerDto.getMarkerDto().getNewPipeMaterial(),
                        markerDto.getMarkerDto().getNewPipeDiameter(),markerDto.getMarkerDto().getNewAntiCorrodedType(),markerDto.getMarkerDto().getNewDesignThickness(),markerDto.getMarkerDto().getNewConstructTime(),
                        protectivePotential,openCircuitPotential,emissionCurrent,refEleCalibration,status,maintain,leftThickness,taskNo,reason,repairMethod,groundType,repairTime
                };
                for (int j = 0; j < content.length; j++) {
                    labels[i + 1][j] = new Label(j, i + 1, content[j]);
                }
            }
            for (int i = 0; i < markerDtos.size() + 1; i++) {
                for (int j = 0; j < titleTxt.length; j++) {
                    sheet.addCell(labels[i][j]);
                }
            }
            book.write();
            book.close(); //最好在finally中关闭，此处仅作为示例不太规范
        } catch (Exception e) {
            logger.error(e.getMessage());
           return false;
        }
        return true;
    }
//TODO LIST：wxl 待更新
    public void exportClientExcel( HttpServletRequest request, HttpServletResponse response,User user, String strMarkerDto) {
        List<MarkerBusinessDto> markerBusinessDtos = null;
        int type = 0;
        String excelName = "管线信息";
        String excelColumns = "";
        String alias = "";
        if (StringUtils.isNotBlank(strMarkerDto)) {
            strMarkerDto = strMarkerDto.replace('\n',' ');
            MarkerDto markerDto = new Gson().fromJson(strMarkerDto, MarkerDto.class);
            markerBusinessDtos = getMarkerBusinessDtos(markerDto, user.getCompany().getId());
            type = markerDto.getRecordType();
        } else {
            markerBusinessDtos = getMarkerBusinessDtos(null, user.getCompany().getId());
        }
        if (type == 1) {
            excelName = "管线信息";
            excelColumns = "markerDto.id,markerDto.markerId,markerDto.ownerComp,markerDto.area,markerDto.antiCorrodedNo,markerDto.projectName," +
                    "markerDto.projectNo,markerDto.line,markerDto.pressLevel,markerDto.pipeDiameter,markerDto.pipeMaterial,markerDto.depth," +
                    "markerDto.antiCorrodedType,markerDto.designThickness,markerDto.road,markerDto.constructTime,markerDto.x_cord,markerDto.y_cord," +
                    "markerDto.longitude,markerDto.latitude,markerDto.memo";
            alias ="编号,标识器ID,权属单位,所属区域,防腐工号,工程名称," +
                    "工程编号,管网编号,压力级制,管径,材质,设计埋深," +
                    "防腐层种类,设计壁厚,所属道路,建设时间,X坐标,Y坐标," +
                    "经度,纬度,备注";
        } else if (type == 2) {
            excelName = "阴保桩信息";
            excelColumns = "markerDto.id,markerDto.markerId,markerDto.markerObjectType,markerDto.ownerComp,markerDto.area,markerDto.line," +
                    "markerDto.road,markerDto.testPile,markerDto.devCode,markerDto.testPileBuildComp,markerDto.x_cord,markerDto.y_cord," +
                    "markerDto.longitude,markerDto.latitude,markerDto.memo";
            alias ="编号,标识器ID,附属物名称,权属单位,所属区域,管网编号," +
                    "所属道路,测试桩名称,设备编码,测试桩建设单位,X坐标,Y坐标," +
                    "经度,纬度,备注";
        } else if (type == 3) {
            excelName = "特征管点信息";
            excelColumns = "markerDto.id,markerDto.markerId,markerDto.markerObjectType,markerDto.ownerComp,markerDto.area,markerDto.antiCorrodedNo,markerDto.projectName," +
                    "markerDto.projectNo,markerDto.line,markerDto.pressLevel,markerDto.pipeDiameter,markerDto.pipeMaterial,markerDto.depth," +
                    "markerDto.antiCorrodedType,markerDto.designThickness,markerDto.road,markerDto.constructTime,markerDto.x_cord,markerDto.y_cord," +
                    "markerDto.longitude,markerDto.latitude,markerDto.memo";
            alias ="编号,标识器ID,特征管点,权属单位,所属区域,防腐工号,工程名称," +
                    "工程编号,管网编号,压力级制,管径,材质,设计埋深," +
                    "防腐层种类,设计壁厚,所属道路,建设时间,X坐标,Y坐标," +
                    "经度,纬度,备注";
        } else if (type == 4) {
            excelName = "抢修点信息";
            excelColumns = "markerDto.id,markerDto.markerId,markerDto.ownerComp,markerDto.area,markerDto.line,markerDto.pipeLength," +
                    "markerDto.pipeDiameter,markerDto.constructTime,markerDto.pressLevel,repairPointDto.leftThickness,markerDto.antiCorrodedType,repairPointDto.reason," +
                    "repairPointDto.repairMethod,repairPointDto.groundType,repairPointDto.repairTime,markerDto.x_cord,markerDto.y_cord," +
                    "markerDto.longitude,markerDto.latitude,markerDto.memo";
            alias ="编号,标识器ID,权属单位,所属区域,管网编号,管线长度," +
                    "管径,建设时间,压力级制,剩余壁厚,防腐层种类,漏气原因," +
                    "修复方式,地面类型,抢修时间,X坐标,Y坐标,经度," +
                    "纬度,备注";
        } else if(type==5){//全部导出的，在地图上
            excelName = "接切线信息";
            excelColumns = "markerDto.id,markerDto.markerId,markerDto.ownerComp,markerDto.area,markerDto.line,markerDto.jobNo," +
                    "markerDto.jobContent,markerDto.pressLevel,markerDto.x_cord,markerDto.y_cord,markerDto.pipeDiameter,markerDto.pipeMaterial," +
                    "markerDto.designThickness,markerDto.antiCorrodedType,markerDto.constructTime,markerDto.newLineNo,markerDto.newPipeDiameter,markerDto.newPipeMaterial," +
                    "markerDto.newDesignThickness,markerDto.newAntiCorrodedType,markerDto.newConstructTime,markerDto.longitude,markerDto.latitude,markerDto.memo";
            alias ="编号,标识器ID,权属单位,所属区域,管网编号,作业编号," +
                    "作业内容,压力级制,X坐标,Y坐标,管径,材质," +
                    "设计壁厚,防腐层种类,建设时间,新管线编号,新管径,新材质," +
                    "新管壁厚度,新防腐层种类,新建设时间,经度,纬度,备注";
        } else {//全部导出的，在地图上
            excelName = "标识器信息";
            excelColumns = "markerDto.id,markerDto.markerId,markerDto.ownerComp,markerDto.area,markerDto.line,markerDto.jobNo," +
                    "markerDto.jobContent,markerDto.pressLevel,markerDto.x_cord,markerDto.y_cord,markerDto.pipeDiameter,markerDto.pipeMaterial," +
                    "markerDto.designThickness,markerDto.antiCorrodedType,markerDto.constructTime,markerDto.newLineNo,markerDto.newPipeDiameter,markerDto.newPipeMaterial," +
                    "markerDto.newDesignThickness,markerDto.newAntiCorrodedType,markerDto.newConstructTime,markerDto.memo";
            alias ="编号,标识器ID,权属单位,所属区域,管网编号,作业编号," +
                    "作业内容,压力级制,X坐标,Y坐标,管径,材质," +
                    "设计壁厚,防腐层种类,建设时间,新管线编号,新管径,新材质," +
                    "新管壁厚度,新防腐层种类,新建设时间,备注";
        }
        String userAgent = request.getHeader("user-agent");
        if (userAgent == null || (userAgent.indexOf("Firefox") < 0 && userAgent.indexOf("Chrome") < 0&& userAgent.indexOf("Safari") < 0)) {
            try {
                excelName= URLEncoder.encode(excelName, "UTF8"); //其他浏览器
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String[] columns = excelColumns.split(",");
        String[] headerAlias = alias.split(",");
        TableModel tableModel = new TableModel();
        tableModel.setName(excelName);
        tableModel.addHeaders(columns);
        tableModel.addHeaderAlias(headerAlias);
        tableModel.setData(markerBusinessDtos);
        response.setCharacterEncoding("UTF-8");
        try {
            exportor.export(request, response, tableModel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List statsGroupByOwnerComp(String beginDate,String endDate,String recordType) throws ParseException {
        Criteria criteria = this.createCriteria(Marker.class);
        if(StringUtils.isNotBlank(beginDate)) {
            criteria.add(Restrictions.ge("createTime", DateUtils.sdf1.parse(beginDate)));
        }
        if(StringUtils.isNotBlank(endDate)) {
            criteria.add(Restrictions.le("createTime", DateUtils.sdf2.parse(endDate + " 23:59:59")));
        }
        if(StringUtils.isNotBlank(recordType)){
            criteria.add(Restrictions.eq("recordType",Integer.valueOf(recordType)));
        }
        criteria.add(Restrictions.eq("isValid",1));
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("ownerComp"))
                        .add(Projections.rowCount())
        );
        return criteria.list();
    }
    public List statsGroupByCreateTime(String beginDate,String endDate,String ownerComp) throws ParseException {
        StringBuilder sql = new StringBuilder("select date_format(createTime,'%Y') as dates,count(1) from marker where 1 = 1 ");
        if(StringUtils.isNotBlank(beginDate)) {
            sql.append(" and createTime >='"+beginDate+"'");
        }
        if(StringUtils.isNotBlank(endDate)) {
            int endYear = Integer.parseInt(endDate)+1;
            sql.append(" and createTime <'"+endYear+"'");
        }
        if(StringUtils.isNotBlank(ownerComp)&&!ownerComp.equals("全部")){
            sql.append(" and ownerComp ='"+ownerComp+"'");
        }
        sql.append(" and isValid=1");
        sql.append(" group by dates");
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
        return query.list();
    }
    public List statsGroupByRecordType(String beginDate,String endDate,String ownerComp){
        Criteria criteria = this.createCriteria(Marker.class);
        if(StringUtils.isNotBlank(beginDate)) {
            try {
                criteria.add(Restrictions.ge("createTime", DateUtils.sdf1.parse(beginDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotBlank(endDate)) {
            try {
                criteria.add(Restrictions.le("createTime", DateUtils.sdf2.parse(endDate + " 23:59:59")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotBlank(ownerComp)&&!ownerComp.equals("全部")){
            criteria.add(Restrictions.eq("ownerComp",ownerComp));
        }
        criteria.add(Restrictions.eq("isValid",1));
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("recordType"))
                        .add(Projections.rowCount())
        );
        return criteria.list();
    }
    public List statsGroupByPressLevel(String beginDate,String endDate,String ownerComp){
        Criteria criteria = this.createCriteria(Marker.class);
        if(StringUtils.isNotBlank(beginDate)) {
            try {
                criteria.add(Restrictions.ge("createTime", DateUtils.sdf1.parse(beginDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotBlank(endDate)) {
            try {
                criteria.add(Restrictions.le("createTime", DateUtils.sdf2.parse(endDate + " 23:59:59")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotBlank(ownerComp)&&!ownerComp.equals("全部")){
            criteria.add(Restrictions.eq("ownerComp",ownerComp));
        }
        criteria.add(Restrictions.eq("isValid",1));
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("pressLevel"))
                        .add(Projections.rowCount())
        );
        return criteria.list();
    }

    /**
     * 已和项目经理确认，时间为巡检运行的时间，并非安装标识器的时间
     * @param beginDate 检测开始时间
     * @param endDate
     * @param ownerComp
     * @param area
     *   暂时不考虑 user
     * @return
     */
    public List statsGroupByTestPileStatus(String beginDate,String endDate,String ownerComp,String area){
         StringBuilder sql = new StringBuilder("select b.status,count(1) from marker m right join businessdata b on m.markerId = b.markerId where 1 = 1 ");
        if(StringUtils.isNotBlank(beginDate)){
            sql.append(" and b.checkTime >='"+beginDate+"'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" and b.checkTime <='"+endDate+" 23:59:59'");
        }
        if(StringUtils.isNotBlank(ownerComp)&&!ownerComp.equals("全部")){
            sql.append(" and m.ownerComp = '"+ownerComp+"'");
        }
        if(StringUtils.isNotBlank(area)&&!area.equals("全部")){
            sql.append(" and m.area ='"+area+"'");
        }
        sql.append(" group by b.status");
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
        return query.list();
    }
//每个季度如果有检测多次，监测或维修结果不同，都要计算上吗？yes
    public List statsTestPileReport(String ownerComp,String year){
        StringBuilder sql = new StringBuilder("select floor((month(b.checkTime)-1)/3) as season,b.status,count(1) from marker m right join businessdata b on m.markerId = b.markerId where 1 = 1 ");
        if(StringUtils.isNotBlank(year)){
            Integer endYear = Integer.valueOf(year)+1;
            sql.append(" and b.checkTime >= '"+year+"' and b.checkTime <'"+String.valueOf(endYear)+"'");
        }
        if(StringUtils.isNotBlank(ownerComp)&&!ownerComp.equals("全部")){
            sql.append(" and m.ownerComp = '"+ownerComp+"'");
        }
        sql.append(" group by season,b.status");
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
        return query.list();
    }

    public List statsGroupByMaintain(String beginDate,String endDate,String ownerComp,String area){
        StringBuilder sql = new StringBuilder("select b.maintain,count(1) from marker m right join businessdata b on m.markerId = b.markerId where 1 = 1 ");
        if(StringUtils.isNotBlank(beginDate)){
            sql.append(" and b.checkTime >='"+beginDate+"'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" and b.checkTime <='"+endDate+" 23:59:59'");
        }
        if(StringUtils.isNotBlank(ownerComp)&&!ownerComp.equals("全部")){
            sql.append(" and m.ownerComp = '"+ownerComp+"'");
        }
        if(StringUtils.isNotBlank(area)&&!area.equals("全部")){
            sql.append(" and m.area ='"+area+"'");
        }
        sql.append(" group by b.maintain");
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
        return query.list();
    }
    //测试桩、井维护处理报表
    public List statsMaintainReport(String beginDate,String endDate){
        StringBuilder sql = new StringBuilder("select m.ownerComp,b.maintain,count(1) from marker m right join businessdata b on m.markerId = b.markerId where 1 = 1 ");
        if(StringUtils.isNotBlank(beginDate)){
            sql.append(" and b.checkTime >='"+beginDate+"'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" and b.checkTime <='"+endDate+" 23:59:59'");
        }
        sql.append(" group by m.ownerComp,b.maintain");
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
        return query.list();
    }
//测试桩、井电位统计
    public List statsGroupByPotentialStatus(String beginDate,String endDate,String ownerComp,String area){
        StringBuilder sql = new StringBuilder("select case when b.protectivePotential>1.2 then '过保护' " +
                "when b.protectivePotential>=0.85 then '合格'" +
                " when b.protectivePotential>0 then '不合格'" +
                " else '无法检测' end as potentialStatus," +
                "count(1) from marker m right join businessdata b on m.markerId = b.markerId where 1 = 1 ");
        if(StringUtils.isNotBlank(beginDate)){
            sql.append(" and b.checkTime >='"+beginDate+"'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" and b.checkTime <='"+endDate+" 23:59:59'");
        }
        if(StringUtils.isNotBlank(ownerComp)&&!ownerComp.equals("全部")){
            sql.append(" and m.ownerComp = '"+ownerComp+"'");
        }
        if(StringUtils.isNotBlank(area)&&!area.equals("全部")){
            sql.append(" and m.area ='"+area+"'");
        }
        sql.append(" group by potentialStatus");
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
        return query.list();
    }
//测试桩、井保护电位报表
    public List statsPotentialReport(String ownerComp,String year){
        StringBuilder sql = new StringBuilder("select floor((month(b.checkTime)-1)/3) as season, case when b.protectivePotential>1.2 then '过保护' " +
                "when b.protectivePotential>=0.85 then '合格'" +
                " when b.protectivePotential>0 then '不合格'" +
                " else '无法检测' end as potentialStatus," +
                "count(1) from marker m right join businessdata b on m.markerId = b.markerId where 1 = 1 ");
        if(StringUtils.isNotBlank(year)){
            Integer endYear = Integer.valueOf(year)+1;
            sql.append(" and b.checkTime >='"+year+"' and b.checkTime < '"+String.valueOf(endYear)+"'");
        }
        if(StringUtils.isNotBlank(ownerComp)&&!ownerComp.equals("全部")){
            sql.append(" and m.ownerComp = '"+ownerComp+"'");
        }
        sql.append(" group by season,potentialStatus");
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
        return query.list();
    }

}
