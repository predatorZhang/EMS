package com.casic.accessControl.core.logback;

import ch.qos.logback.core.PropertyDefinerBase;
import ch.qos.logback.core.util.Loader;

import java.net.URL;

public class ResourceExistsPropertyDefiner extends PropertyDefinerBase {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPropertyValue() {
        if (path == null) {
            return "false";
        }

        URL resourceURL = Loader.getResourceBySelfClassLoader(path);

        return (resourceURL != null) ? "true" : "false";
    }
}
