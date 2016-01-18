package cc.linkedme.dao;

import java.util.Map;
import java.util.Set;

/**
 * Created by qipo on 15/9/5.
 */
public interface LMRedisDao {


    /**
     * delete keys
     */

    Object del(String... keys);

    /**
     * set the key, value byte and live time
     */

    void set(byte[] key, byte[] value, long liveTime);

    /**
     * set the key, value and live time
     */

    void set(String key, String value, long liveTime);

    /**
     * set the key and value
     */

    void set(String key, String value);

    /**
     * set the key and value byte
     */

    void set(byte[] key, byte[] value);

    /**
     * get the value of this key
     */

    Object get(String key);

    /**
     * get those key that match this pattern
     */

    Set Setkeys(String pattern);

    /**
     * judge the key exist
     */

    boolean exists(String key);

    /**
     * flush redis database
     */

    Object flushDB();

    /**
     * look the size of database
     */

    Object sizeDB();

    /**
     * ping the host server
     */
    Object ping();

    /**
     * map operation
     */

    void setMap(String name, Map<String, Object> map, final long expire);

    Object getMap(String name, String hashKey);

    void deleteMap(String name, String... hashKeys);

    /**
     * Object string operation
     */

    void setObject(Object key, Object value, final long expire);

    Object getObject(Object key);

    void deleteObject(Object key);


}
