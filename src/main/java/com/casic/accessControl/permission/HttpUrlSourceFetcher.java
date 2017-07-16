package com.casic.accessControl.permission;

import com.casic.accessControl.core.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlSourceFetcher
  implements UrlSourceFetcher
{
  private static Logger logger = LoggerFactory.getLogger(HttpUrlSourceFetcher.class);
  public static final int DEFAULT_BUFFER_SIZE = 1024;
  private String url;
  private String appId;
  private String relativePath;
  
  public String getRelativePath() {
	return relativePath;
}

public void setRelativePath(String relativePath) {
	this.relativePath = relativePath;
}

public UserObj getSource(String username,String password)
  {
    try
    {
      this.url=this.relativePath;
     //add the appId parameter
      if ((this.url.indexOf("?appId=") == -1) || (this.url.indexOf("&appId=") == -1))
      {
        if (this.url.indexOf('?') != -1)
          this.url += "&";
        else {
          this.url += "?";
        }

        this.url = (this.url + "appId=" + this.appId);
      }

      if ((this.url.indexOf("?username=") == -1) || (this.url.indexOf("&username=") == -1))
      {
        if (this.url.indexOf('?') != -1)
          this.url += "&";
        else {
          this.url += "?";
        }
        this.url = (this.url + "username=" + username);
        this.url += "&";
        this.url = (this.url + "password=" + password);
      }
      
      logger.info(this.url);
      HttpURLConnection conn = (HttpURLConnection)new URL(this.url).openConnection();

      InputStream is = conn.getInputStream();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] b = new byte[1024];
      int len = 0;

      while ((len = is.read(b, 0, 1024)) != -1) {
        baos.write(b, 0, len);
      }

      is.close();
      baos.flush();
      baos.close();

      String content = new String(baos.toByteArray(), "UTF-8");
      logger.info(content);

      JsonMapper jsonMapper = new JsonMapper();
      UserObj userObj = (UserObj)jsonMapper.fromJson(content, UserObj.class);

      return userObj;
      
    } catch (Exception ex) {
      logger.error("", ex);
      throw new RuntimeException(ex);
    }
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

}