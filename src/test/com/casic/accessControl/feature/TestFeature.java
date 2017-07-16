//package com.casic.accessControl.feature;
//
//import com.casic.accessControl.feature.dto.OrgDto;
//import com.casic.accessControl.feature.manager.FeatureManager;
//import com.casic.accessControl.user.domain.Company;
//import com.casic.accessControl.user.domain.User;
//import com.casic.accessControl.user.manager.UserManager;
//import com.casic.accessControl.basic.BasicTest;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import org.junit.Test;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by lenovo on 2016/6/7.
// */
//
//public class TestFeature extends BasicTest {
//    @Resource
//    private FeatureManager featureManager;
//    @Resource
//    private UserManager userManager;
//    @Test
//    public void resovleFeatureList() {//测试解析ResolveList
//        User user = userManager.getUserById(1L);//获取当前登录用户
//        Company company = user.getCompany();
//        String orgList = featureManager.getOrgList(1,company);
//        System.out.println(orgList);
//        Gson gson = new Gson();
//        List<OrgDto> orgDtoList = gson.fromJson(orgList, new TypeToken<List<OrgDto>>() {
//        }.getType());
//        List<OrgDto> children = new ArrayList<OrgDto>();
//        List<OrgDto> grandChildren = new ArrayList<OrgDto>();
//
//        for(OrgDto orgDto :orgDtoList){
//            System.out.println(orgDto.getName());
//            children.addAll(orgDto.getChildren());
//        }
//        for(OrgDto orgDto:children){
//            System.out.println(orgDto.getName());
//            grandChildren.addAll(orgDto.getChildren());
//        }
//        for(OrgDto orgDto:grandChildren){
//            System.out.println(orgDto.getName());
//        }
//    }
//
////    @Test
////    public String long2String(Long param){
////       if(param==null){
////           return "";
////       }else if()
////    }
//
//
//}
