//package com.casic.accessControl.rs;
//
////import com.casic.iot.feature.domain.Feature;
////import com.casic.iot.feature.manager.FeatureManager;
//import com.casic.iot.marker.domain.Marker;
//import com.casic.iot.marker.manager.MarkerManager;
//import com.casic.iot.user.domain.Company;
//import com.casic.iot.user.domain.User;
//import com.casic.iot.user.manager.UserManager;
//import com.google.gson.Gson;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
//* Created by admin on 2015/1/15.
//*/
//@Component
//@Path("mark")
//public class MarkResource
//{
//    private static Logger logger = LoggerFactory.getLogger(MarkResource.class);
//
//    @Resource
//    private UserManager userManager;
//
//    @Resource
//    private MarkerManager markerManager;
//
////    @Resource
////    private FeatureManager featureManager;
//
//    @GET
//    @Path("post")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Map update(@QueryParam("userName") String userName,
//                      @QueryParam("markerId") String markerId,
//                      @QueryParam("owerId") String owerId,
//                      @QueryParam("markerType") String markerType,
//                      @QueryParam("depth") String depth,
//                      @QueryParam("mileage") String mileage,
//                      @QueryParam("mileage2") String mileage2,
//                      @QueryParam("createTime") String createTime,
//                      @QueryParam("longitude") String longitude,
//                      @QueryParam("latitude") String latitude,
//                      @QueryParam("markerName") String markerName,
//                      @QueryParam("memo") String memo)
//    {
//        Map map = new HashMap<String, Object>();
//        try
//        {
//            User user = userManager.getUserByName(userName);
//            if (user == null) {
//                map.put("success", false);
//                map.put("message", "人员不存在！");
//                return map;
//            }
//            Gson gson = new Gson();
//            Marker marker = new Marker();
//            marker.setMarkerId(markerId);
//            marker.setMarkerType(markerType);
//            marker.setDepth(marker.getDepth());
//            marker.setCompanyId(Long.parseLong(owerId));
////            Feature feature = featureManager.getFeatureById(Long.parseLong(owerId));
////            marker.setBelongFeature(feature);
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            if (createTime!=null)
//                marker.setCreateTime(sdf.parse(createTime));
//            marker.setLatitude(Double.parseDouble(latitude));
//            marker.setLongitude(Double.parseDouble(longitude));
////            marker.setMarkerName(markerName);
////            marker.setMileage(mileage);
////            marker.setMileage2(mileage2);
//            marker.setMemo(memo);
//            boolean temp = markerManager.saveMarker(marker);
//            if (temp)
//            {
//                map.put("success", true);
//                map.put("message", "上传成功");
//            }
//            else
//            {
//                map.put("success", false);
//                map.put("message", "上传失败");
//            }
//        }
//        catch (Exception e)
//        {
//            map.put("success", false);
//            map.put("message", e.getMessage());
//        }
//        return map;
//    }
//}
