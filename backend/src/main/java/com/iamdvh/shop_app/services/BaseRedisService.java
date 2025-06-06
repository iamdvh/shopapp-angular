package com.iamdvh.shop_app.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseRedisService {
    void set(String key, String value);

    void setTimeToLive(String key, long timeToLive);

    void hashSet(String key, String field, Object value);

    boolean hashExists(String key, String field);

    Object get(String key);

    Map<String, Object> getField(String key);

    Object hashGet(String key, String field);

    List<Object> hashGetByPrefix(String key, String prefix);

    Set<String> getFieldPrefixes(String key);

    void delete(String key);

    void delete(String key, String field);

    void delete(String key, List<String> fields);
}
