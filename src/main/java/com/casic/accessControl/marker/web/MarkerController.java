package com.casic.accessControl.marker.web;

import com.casic.accessControl.core.ext.export.CsvExportor;
import com.casic.accessControl.core.ext.export.TableModel;
import com.casic.accessControl.marker.domain.BusinessData;
import com.casic.accessControl.marker.domain.Marker;
import com.casic.accessControl.marker.dto.BusinessDataDto;
import com.casic.accessControl.marker.dto.MarkerBusinessDto;
import com.casic.accessControl.marker.dto.MarkerDto;
import com.casic.accessControl.marker.dto.RepairPointDto;
import com.casic.accessControl.marker.manager.BusinessDataManager;
import com.casic.accessControl.marker.manager.MarkerManager;
import com.casic.accessControl.marker.manager.RepairPointManager;
import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.User;
import com.casic.accessControl.user.manager.UserManager;
import com.casic.accessControl.util.DataTable;
import com.casic.accessControl.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.collections.CollectionUtils;
import org.geotools.styling.Mark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by lenovo on 2016/5/16.
 */
@Controller
@RequestMapping("marker")
public class MarkerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MarkerManager markerManager;
    @Resource
    private UserManager userInfoManager;
   @Resource
   private BusinessDataManager businessDataManager;
    @Resource
    private RepairPointManager repairPointManager;

    @RequestMapping("get-markers-data")
    @ResponseBody
    public Map<String, Object> getAllPoint(@RequestParam(required = false) String strMarkerDto,HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = (User) session.getAttribute(StringUtils.SYS_USER);//获取当前登录用户
        MarkerDto markerDto = null;
        if(StringUtils.isNotBlank(strMarkerDto)){
             markerDto = new Gson().fromJson(strMarkerDto, MarkerDto.class);
        }
        List<MarkerBusinessDto> data = markerManager.getMarkerBusinessDtos(markerDto,user.getCompany().getId());
        result.put("data", data);
        result.put("count", data.size());
        return result;
    }
    /**
     * 获取标识器列表，前端展示列表使用
     *
     * @param jsonParam
     * @param markerDto
     * @param session
     * @return
     */
    @RequestMapping("page-query-markers")
    @ResponseBody
    public String list(String jsonParam, String markerDto, HttpSession session) {
        try {
            User currentUser = (User) session.getAttribute(StringUtils.SYS_USER);
            DataTable<MarkerDto> markerList = markerManager.pageQueryMarker(jsonParam, markerDto, currentUser.getCompany().getId());
            DataTable<MarkerBusinessDto> markerBusinessDtos = new DataTable<MarkerBusinessDto>();
            markerBusinessDtos.setAaData(markerManager.convertMarkerBusinessDtos(markerList.getAaData()));
            markerBusinessDtos.setiTotalRecords(markerList.getiTotalRecords());
            markerBusinessDtos.setsEcho(markerList.getsEcho());
            markerBusinessDtos.setiTotalDisplayRecords(markerList.getiTotalDisplayRecords());
            Gson gson = new Gson();
            String json = gson.toJson(markerBusinessDtos);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping("save-marker")
    @ResponseBody
    public Map<String, Object> save(@Validated @ModelAttribute MarkerDto markerDto,
                                    BindingResult bindingResult, HttpSession session) {
        try {
            if (bindingResult.hasErrors()) {
                List<ObjectError> objectErrors = bindingResult.getAllErrors();
                for (ObjectError objectError : objectErrors) {
                    System.out.println(new String(objectError.getDefaultMessage().getBytes("ISO-8859-1"), "UTF-8"));

                }
            }
        } catch (Exception e) {

        }

        Map<String, Object> map = new HashMap<String, Object>();
        if (markerDto == null) {
            map.put("message", "操作失败，要保存的信息为空，请仔细检查表单值");
            return map;
        }
        User user = (User) session.getAttribute(StringUtils.SYS_USER);//获取当前登录用户
        Long companyId = user.getCompany().getId();
        try {
            List<MarkerDto> markerDtos = new ArrayList<MarkerDto>();
            markerDtos.add(markerDto);
            markerManager.batchSave(markerDtos, companyId);
            map.put("success", true);
            map.put("message", "编辑成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "编辑失败");
        }


        return map;
    }
//TODO List 探测仪保存请求，repairDataDto

    //更改为单个保存
    @RequestMapping("save-marker-na")
    @ResponseBody
    public Map<String, Object> batchSaveNa(@RequestParam(value = "marker", required = true) String strMarker,
                                           @RequestParam(value= "businessData",required = false) String businessData,
                                           @RequestParam(value = "repairPointDto",required = false) String repairPointDto) {
        strMarker = strMarker.replace('\n',' ');
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "安装失败，或无标识器信息");
        map.put("success", false);
        Gson gson = new Gson();
        boolean result = true;
        try {
            MarkerDto markers = gson.fromJson(strMarker, new TypeToken<MarkerDto>() {
            }.getType());
            if (markers==null) {//批量导入数据不存在，直接返回
                return map;
            }
            if(markers.getMarkerId()==null){
                map.put("message", "未读取标识器ID");
                return map;
            }
            if(markers.getLongitude()==null||markers.getLatitude()==null){
                map.put("message", "经纬度为空，请确保已获取定位");
                return map;
            }
            if(markerManager.getMarkerByMarkerId(markers.getMarkerId()).size()>0){
                map.put("message", "标识器已存在,请勿重复安装");
                return map;
            }
            if(StringUtils.isNotBlank(businessData)) {
                businessData = businessData.replace('\n',' ');
                BusinessDataDto businessDataDto = gson.fromJson(businessData,new TypeToken<BusinessDataDto>() {
                }.getType());
                businessDataDto.setMarkerId(markers.getMarkerId());//前端未传markerId，在此关联
                result = businessDataManager.saveBusinessData(businessDataDto);
            }
            if(StringUtils.isNotBlank(repairPointDto)) {
                repairPointDto = repairPointDto.replace('\n',' ');
                RepairPointDto repairPointDto1 = gson.fromJson(repairPointDto,new TypeToken<RepairPointDto>() {
                }.getType());
                repairPointDto1.setMarkerId(markers.getMarkerId());//前端未传markerId，在此关联
                result = result&&repairPointManager.saveRepairData(repairPointDto1);
            }
            Long companyId = markers.getCompanyId();
            if (companyId == null) {
                return map;
            }
            List<MarkerDto> markerList = new ArrayList<MarkerDto>();
            markerList.add(markers);
            if (!(result&&markerManager.batchSave(markerList, companyId))) {
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        map.put("success", true);
        map.put("message", "保存成功");
        return map;
    }

    /**
     * 查询出要编辑的标签信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("edit-marker")
    public String edit(@RequestParam(value = "id", required = false) Long id, Model model) {
        MarkerDto markerDto = new MarkerDto();
        if (null != id) {
            markerDto = markerManager.getMarkerById(id);
        }

        model.addAttribute("model", markerDto);
        return "marker/marker-info-edit";
    }

    @RequestMapping("delete-marker")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (id == null) {
            map.put("success", false);
            map.put("message", "删除需要ID信息");
            return map;
        }
        markerManager.delMarkerByIds(String.valueOf(id));
        map.put("success", true);
        map.put("message", "删除成功");
        return map;
    }


    @RequestMapping("delete-markers-by-id")
    @ResponseBody
    public Map<String, Object> delByIds(@RequestParam(required = true) String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(ids)) {
            map.put("message", "删除需要ID信息");
            return map;
        }
        markerManager.delMarkerByIds(ids);
        map.put("success", true);
        map.put("message", "删除成功");
        return map;
    }

    @RequestMapping("export")
    public void export2Excel(@RequestParam(required = false) String strMarkerDto,
                             HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        User user = (User) session.getAttribute(StringUtils.SYS_USER);//获取当前登录用户
        markerManager.exportClientExcel(request,response,user,strMarkerDto);
    }


    /**
     * 探测仪调用服务器端的导出功能，需要同步的时候，点击同步，调用此方法，服务器将标识器信息下载到指定的目录
     */
    @RequestMapping("exportMarker")
    @ResponseBody
    public Map exportMarker(@RequestParam(required = true) Long userId, HttpServletRequest request) {//用户id前端传的companyId
        Map<String, Object> result = new HashMap<String, Object>();

        boolean flag = markerManager.exportServerExcel(userId, request.getServletContext().getRealPath("/") + "/xls/marker" + userId + ".xls");
        if (!flag) {
            result.put("message", "导出失败");
            result.put("success", false);
        }
        result.put("message", "导出成功");
        result.put("success", true);
        return result;
    }

    /**
     * 批量导入功能
     * @param file 导入的Excel文件，.xls格式
     * @param session
     * @return
     */
    @RequestMapping(value = "import-markers")
    @ResponseBody
    public Map<String, Object> excelMarkers(@RequestParam(value = "excelFile", required = true) MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute(StringUtils.SYS_USER);//获取当前登录用户
        Long companyId = user.getCompany().getId();

        String fileName = file.getOriginalFilename();
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("message", "导入失败");
        res.put("success", false);
        if (companyId == null) {
            res.put("message", "用户未登录，导入失败！");
            return res;
        }
        if (!fileName.endsWith(".xls")) {//仅支持xls格式的导入
            res.put("message", "导入失败，系统仅支持xls格式文件上传");
            return res;
        }
        int type = 1;
        if(fileName.contains("阴保桩")) type = 2;
        else if(fileName.contains("特征管点")) type = 3;
        else if(fileName.contains("抢修位置")) type = 4;
        else if(fileName.contains("接切线")) type = 5;
        int flag = 0;
        //保存
        try {
            flag = markerManager.readExcel(file.getInputStream(), companyId,type);
        } catch (IOException e) {
            e.printStackTrace();
            return res;
        }

        if(flag > 0){
            //提示友好改进
            if (flag == 1) {//Excel表不含内容返回1
                res.put("message", "Excel表无有效内容，导入失败");
                return res;
            }
            if (flag == 3) {
                res.put("message", "经纬度坐标包含无效数据，请检查是否全部为数字，导入失败");
                return res;
            }
            return res;
        }
        res.put("success", true);
        res.put("message", "导入成功");
        return res;
    }

    /**
     * 统计开始日期和结束日期之间的各个权属单位标识器个数
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param recordType 标识器类型
     * @return
     */
    @RequestMapping(value = "stats-by-ownercomp")
    @ResponseBody
    public Map<String,Object> statsByOwnerComp(@RequestParam(required = false,value = "beginDate")String beginDate,
                                                   @RequestParam(required = false,value = "endDate")String endDate,
                                                   @RequestParam(required = false,value = "recordType")String recordType){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = null;
        try {
            list = markerManager.statsGroupByOwnerComp(beginDate,endDate,recordType);
        } catch (ParseException e) {
            result.put("success",false);
            return result;
        }
        result.put("data",list);
        result.put("success",true);
        return result;
    }

    /**
     * 统计开始日期和结束日期之间的各个权属单位标识器个数
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param ownerComp 所属公司
     * @return
     */
    @RequestMapping(value = "stats-by-createTime")
    @ResponseBody
    public Map<String,Object> statsByCreateTime(@RequestParam(required = false,value = "beginDate")String beginDate,
                                                   @RequestParam(required = false,value = "endDate")String endDate,
                                                   @RequestParam(required = false,value = "ownerComp")String ownerComp){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = null;
        try {
            list = markerManager.statsGroupByCreateTime(beginDate,endDate,ownerComp);
        } catch (ParseException e) {
            result.put("success",false);
            return result;
        }
        result.put("data",list);
        result.put("success",true);
        return result;
    }
    @RequestMapping(value = "stats-by-recordType")
    @ResponseBody
    public Map<String,Object> statsByRecordType(@RequestParam(required = false,value = "beginDate")String beginDate,
                                                    @RequestParam(required = false,value = "endDate")String endDate,
                                                    @RequestParam(required = false,value = "ownerComp")String ownerComp){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = markerManager.statsGroupByRecordType(beginDate,endDate,ownerComp);
        result.put("data",list);
        result.put("success",true);
        return result;
    }
    @RequestMapping(value = "stats-by-pressLevel")
    @ResponseBody
    public Map<String,Object> statsGroupByPressLevel(@RequestParam(required = false,value = "beginDate")String beginDate,
                                                         @RequestParam(required = false,value = "endDate")String endDate,
                                                         @RequestParam(required = false,value = "ownerComp")String ownerComp){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = markerManager.statsGroupByPressLevel(beginDate,endDate,ownerComp);
        result.put("data",list);
        result.put("success",true);
        return result;
    }
    @RequestMapping(value = "stats-by-testPileStatus")
    @ResponseBody
   public Map<String,Object> statsGroupByTestPileStatus(String beginDate,String endDate,String ownerComp,String area){
       Map<String,Object> result = new HashMap<String,Object>();
       List list = markerManager.statsGroupByTestPileStatus(beginDate,endDate,ownerComp,area);
       result.put("data",list);
       result.put("success",true);
       return result;
   }
    @RequestMapping(value = "stats-by-testPileReport")
    @ResponseBody
    public Map<String,Object> statsTestPileReport(String ownerComp,String year){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = markerManager.statsTestPileReport(ownerComp,year);
        result.put("data",list);
        result.put("success",true);
        return result;
    }

    @RequestMapping(value = "stats-by-maintain")
    @ResponseBody
    public Map<String,Object> statsGroupByMaintain(String beginDate,String endDate,String ownerComp,String area){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = markerManager.statsGroupByMaintain(beginDate,endDate,ownerComp,area);
        result.put("data",list);
        result.put("success",true);
        return result;
    }

    @RequestMapping(value = "stats-by-maintainReport")
    @ResponseBody
    public Map<String,Object> statsMaintainReport(String beginDate,String endDate){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = markerManager.statsMaintainReport(beginDate,endDate);
        result.put("data",list);
        result.put("success",true);
        return result;
    }
    @RequestMapping(value = "stats-by-potentialStatus")
    @ResponseBody
    public Map<String,Object> statsGroupByPotentialStatus(String beginDate,String endDate,String ownerComp,String area){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = markerManager.statsGroupByPotentialStatus(beginDate,endDate,ownerComp,area);
        result.put("data",list);
        result.put("success",true);
        return result;
    }
    @RequestMapping(value = "stats-by-potentialReport")
    @ResponseBody
    public Map<String,Object> statsPotentialReport(String ownerComp,String year){
        Map<String,Object> result = new HashMap<String,Object>();
        List list = markerManager.statsPotentialReport(ownerComp,year);
        result.put("data",list);
        result.put("success",true);
        return result;
    }
}
