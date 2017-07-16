package com.casic.accessControl.core.ext.export;

import com.casic.accessControl.core.util.ServletUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsvExportor implements Exportor {
    private String encoding = "GBK";

    public void export(HttpServletRequest request,
                       HttpServletResponse response, TableModel tableModel)
            throws IOException {
        StringBuilder buff = new StringBuilder();
        if(tableModel.hasAlias()) {//有设置别名，应该按别名展示
            for (int i = 0; i < tableModel.getHeaderCount(); i++) {
                buff.append(tableModel.getHeaderAlias(i));
                if (i != (tableModel.getHeaderCount() - 1)) {
                    buff.append(",");
                }
            }
        }else{
            for (int i = 0; i < tableModel.getHeaderCount(); i++) {
                buff.append(tableModel.getHeader(i));

                if (i != (tableModel.getHeaderCount() - 1)) {
                    buff.append(",");
                }
            }
        }


        buff.append("\n");

        for (int i = 0; i < tableModel.getDataCount(); i++) {
            for (int j = 0; j < tableModel.getHeaderCount(); j++) {
                buff.append(tableModel.getValue(i, j));

                if (j != (tableModel.getHeaderCount() - 1)) {
                    buff.append(",");
                }
            }

            buff.append("\n");
        }

        response.setContentType(ServletUtils.STREAM_TYPE);
        ServletUtils.setFileDownloadHeader(request, response,
                tableModel.getName() + ".csv");
        response.getOutputStream().write(buff.toString().getBytes(encoding));
        response.getOutputStream().flush();
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}

