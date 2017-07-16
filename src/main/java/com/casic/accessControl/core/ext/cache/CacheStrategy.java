package com.casic.accessControl.core.ext.cache;

public interface CacheStrategy {
    Cache getCache(String name);
}
