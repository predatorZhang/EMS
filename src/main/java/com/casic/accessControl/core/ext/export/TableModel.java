package com.casic.accessControl.core.ext.export;

import com.casic.accessControl.core.util.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TableModel {
    private static Logger logger = LoggerFactory.getLogger(TableModel.class);
    private String name;
    private List<String> headers = new ArrayList<String>();
    private List data;

    //wxl新增
    private List<String> headerAlias = new ArrayList<String>();
    private boolean enableAlias = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addHeaders(String... header) {
        if (header == null) {
            return;
        }

        for (String text : header) {
            if (text == null) {
                continue;
            }

            headers.add(text);
        }
    }
    //新增，可展示自定义名称
    public void addHeaderAlias(String... alias) {
        if (alias == null||alias.length==0|| StringUtils.isEmpty(alias[0])) {
            enableAlias = false;
            return;
        }
         enableAlias = true;
        for (String text : alias) {
            if (text == null) {
                continue;
            }

            headerAlias.add(text);
        }
    }
    public void setData(List data) {
        this.data = data;
    }

    public int getHeaderCount() {
        return headers.size();
    }

    public int getDataCount() {
        return data.size();
    }

    public String getHeader(int index) {
        return headers.get(index);
    }

    public boolean hasAlias(){
        return this.enableAlias;
    }
    public String getHeaderAlias(int index) {
        return headerAlias.get(index);
    }
    public String getValue(int i, int j) {
        try {
            String header = getHeader(j);
            Object value = null;
            String [] headerLink = header.split("\\.");//用.来表示层次
                Object object = data.get(i);
                for(int k = 0;k<headerLink.length;k++){
                    String methodName = ReflectUtils
                            .getGetterMethodName(object, headerLink[k]);
                    value = ReflectUtils.getMethodValue(object, methodName);
                    object = value;
                }
            return (value == null) ? "" : value.toString();
        } catch (Exception ex) {
            logger.info("error", ex);

            return "";
        }
    }
}
