package com.casic.accessControl.core.i18n;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.*;
import java.io.IOException;

public class AcceptLanguageHeaderFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            LocaleContextHolder.setLocale(request.getLocale());
            filterChain.doFilter(request, response);
        } finally {
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
