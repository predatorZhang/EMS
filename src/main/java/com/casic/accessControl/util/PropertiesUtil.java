package com.casic.accessControl.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	private Properties properties;
	private String propName;
	private Map<String, String> memoryPropMap = new HashMap<String, String>();

	public PropertiesUtil(String propFileName) {
		properties=new Properties();
		this.propName = propFileName;
	}

	public String getProperty(String key)
			throws InvalidPropertiesFormatException, IOException {
			String retVal = "";  
		    InputStream in = this.getClass().getResourceAsStream("/" + propName);
		    properties.load(in);
		    retVal = properties.getProperty(key).trim();
		    in.close(); 
		return retVal;
	}
	public static void main(String[] args) {  
		   
		 PropertiesUtil putil = new PropertiesUtil("application.properties");
		 try {
			String csvfileurl = putil.getProperty("rqmodel.url");
			 System.out.println(csvfileurl);
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
