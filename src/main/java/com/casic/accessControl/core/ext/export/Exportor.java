package com.casic.accessControl.core.ext.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Exportor {
    void export(HttpServletRequest request,HttpServletResponse response, TableModel tableModel)
            throws IOException;
}
