package com.casic.accessControl.maker;

import com.casic.accessControl.util.HttpRequestUtils;
import com.casic.accessControl.basic.BasicTest;
import com.casic.accessControl.marker.dto.MarkerDto;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/30.
 */
public class MarkerTest extends BasicTest{

    @Test
    public void testSaveMarker() {
        MarkerDto markerDto = new MarkerDto();
        markerDto.setRecordType(1);
        markerDto.setMarkerObjectType("供热管线");
        markerDto.setDepth("1.2m");
        markerDto.setCompanyId(4L);
        markerDto.setConstructTime("1990-04-02");
        List<MarkerDto> markerDtoList = new ArrayList<MarkerDto>();
        markerDtoList.add(markerDto);
        Gson gson = new Gson();
        String markers = gson.toJson(markerDtoList);
        String s = HttpRequestUtils.sendGet("http://localhost:8080/ems/marker/batchSave-na.do","markers="+markers);
        System.out.println(s);

    }
}
