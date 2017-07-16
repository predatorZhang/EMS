package com.casic.accessControl.cnt;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

/**
 * Created by test203 on 2015/10/22.
 */
public class MySessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext application = session.getServletContext();
        HashSet sessions = (HashSet)application.getAttribute("sessions");
        if(sessions == null){
            sessions = new HashSet();
            application.setAttribute("sessions",sessions);
        }
        sessions.add(session);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext applications = session.getServletContext();
        HashSet sessions = (HashSet)applications.getAttribute("sessions");
        sessions.remove(session);
    }
}
