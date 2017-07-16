package com.casic.accessControl.permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Permission
{
  public static final int MAX_PART_COUNT = 3;
  private static Logger logger = LoggerFactory.getLogger(Permission.class);
  private String region;
  private String resource;
  private String operation;

  public Permission(String text)
  {
    String[] array = text.split(":");

    if (array.length == 1) {
      logger.debug("there must 2 or 3 parts in text : [{}]", text);
      this.region = "system";
      this.resource = array[0];
      this.operation = "*";
      return;
    }

    this.region = array[0];
    this.resource = array[1];

    if (array.length == 3)
      this.operation = array[2];
    else
      this.operation = "*";
  }

  public String getRegion()
  {
    return this.region;
  }

  public String getResource() {
    return this.resource;
  }

  public String getOperation() {
    return this.operation;
  }
}