package com.casic.accessControl.core.auth;

import com.casic.accessControl.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by admin on 2015/3/3.
 */
public class AuthFilter implements Filter

{
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(StringUtils.SYS_USER) == null) {
            logger.info(request.getRequestURI());
            if ((request.getContextPath() + "/").equals(request.getRequestURI())) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else {
//                response.sendRedirect(request.getContextPath() + "/user/ill-login.do?url="+request.getRequestURI());
                response.sendRedirect(request.getContextPath());
            }
            return;
        } else if ((request.getContextPath() + "/").equals(request.getRequestURI())) {
            response.sendRedirect(request.getContextPath() + "/content/ems/ems.jsp");
        }
        filterChain.doFilter(request, response);
    }
}
