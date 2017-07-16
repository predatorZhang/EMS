package com.casic.accessControl.permission;

import org.springframework.stereotype.Service;

public class PermissionDecorator
{
  public String decode(String permission, String region)
  {
    StringBuffer buff = new StringBuffer();
    String regionText = "system";

    if (region != null) {
      regionText = region;
    }

    for (String want : permission.split(","))
      buff.append(regionText).append(":").append(want).append(",");
    String text;
    if (permission.length() != 0) {
      buff.deleteCharAt(buff.length() - 1);
      text = buff.toString();
    } else {
      text = "";
    }
    return text;
  }
}
