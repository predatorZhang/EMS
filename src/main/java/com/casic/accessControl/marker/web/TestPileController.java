package com.casic.accessControl.marker.web;

import com.casic.accessControl.marker.dto.BusinessDataDto;
import com.casic.accessControl.marker.manager.BusinessDataManager;
import com.casic.accessControl.util.DataTable;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by lenovo on 2017/6/23.
 */
@Controller
@RequestMapping(value = "tp")
public class TestPileController {
    @Resource
    private BusinessDataManager businessDataManager;

    @RequestMapping(value = "page-query-test-pile")
    @ResponseBody
    public String pageQueryTestPile(String jsonParam,String beginDate,String endDate,String testPile){
        DataTable<BusinessDataDto>  testPileDatas = null;
        try {
            testPileDatas = businessDataManager.getBusinessDataTable(jsonParam,beginDate,endDate,testPile);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String json = gson.toJson(testPileDatas);
        return json;
    }

    @RequestMapping(value = "export-tp")
    public void export(String beginDate,String endDate,String testPile,HttpServletRequest request, HttpServletResponse response){
        try {
            businessDataManager.exportExcel(beginDate,endDate,testPile,request,response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
