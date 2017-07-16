package com.casic.accessControl.event;

import com.casic.accessControl.util.HttpRequestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lenovo on 2016/8/31.
 */
public class MainTest {
    public void testGetEventList(){
        String s = HttpRequestUtils.sendGet("http://localhost:8080/ems/event/get-event-list.do", "taskId=4" );
        System.out.println(s);
    }
    public void testSaveEvent(){
//       @RequestParam(value = "taskId",required = true) String taskId,
//        @RequestParam(value = "fileBuffer1",required = false) MultipartFile fileBuffer1,
//        @RequestParam(value = "fileBuffer2",required = false) MultipartFile fileBuffer2,
//        @RequestParam(value = "fileBuffer3",required = false) MultipartFile fileBuffer3,
//        @RequestParam(value = "data",required = true) String eventInfo

        String s = HttpRequestUtils.sendGet("http://localhost:8080/ems/event/save-event.do", "taskId=4" );
        System.out.println(s);
    }
    public static void main(String [] args){
       MainTest mainTest = new MainTest();
        mainTest.testGetEventList();

    }
}
