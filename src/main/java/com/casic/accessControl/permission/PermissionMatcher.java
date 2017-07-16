package com.casic.accessControl.permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PermissionMatcher
{
  private static Logger logger = LoggerFactory.getLogger(PermissionMatcher.class);
  private boolean readOnly;

  public boolean match(String want, String have)
  {
    logger.debug("want : {}", want);
    logger.debug("have : {}", have);

    Permission wantPermission = new Permission(want);
    Permission havePermission = new Permission(have);

    if (!checkRegionPart(wantPermission.getRegion(), havePermission.getRegion()))
    {
      logger.debug("check region false");

      return false;
    }

    if (!checkPart(wantPermission.getResource(), havePermission.getResource()))
    {
      logger.debug("check resource false");

      return false;
    }

    String haveOperation = this.readOnly ? "read" : havePermission.getOperation();

    if (checkPart(wantPermission.getOperation(), haveOperation)) {
      logger.debug("check opertion true");

      return true;
    }

    logger.debug("check opertion false");

    return false;
  }

  private boolean checkRegionPart(String want, String have)
  {
    if (want.indexOf(',') == -1) {
      return (checkPart(want, have)) || (checkRegionWithStar(want, have));
    }

    for (String partOfWant : want.split(",")) {
      if (checkRegionPart(partOfWant, have)) {
        return true;
      }
    }

    return false;
  }

  private boolean checkRegionWithStar(String want, String have) {
    if (!want.endsWith("*")) {
      return false;
    }

    String prefix = want.substring(0, want.length() - 1);

    return have.startsWith(prefix);
  }

  private boolean checkPart(String want, String have) {
    if (("*".equals(want)) || ("*".equals(have))) {
      return true;
    }

    return want.equals(have);
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public boolean isReadOnly() {
    return this.readOnly;
  }
}