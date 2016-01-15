package cc.linkedme.service;

import java.util.Map;

/**
 * Created by qipo on 15/9/5.
 */
public interface LMRedisService {

    Object ping();

    void set(String key, String value);

    String get(String key);

    /**
     * map operation
     */
    void setMap(String name, Map<String, Object> map, long expire);

    Object getMap(String name, String hashKey);

    void deleteMap(String name, String... hashKeys);

    /**
     * Object string operation
     */

    void setObject(Object key, Object value, long expire);

    Object getObject(Object key);

    void deleteObject(Object key);

    Object flushDB();

    long sizeDB();

    boolean exists(final String key);



}
