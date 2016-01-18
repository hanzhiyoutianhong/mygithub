package cc.linkedme.service.impl;

import cc.linkedme.dao.LMRedisDao;
import cc.linkedme.service.LMRedisService;

import java.util.Map;

/**
 * Created by qipo on 15/9/5.
 */
public class LMRedisServiceImpl implements LMRedisService {

    private LMRedisDao lmRedisDao;

    public Object ping() {
        return lmRedisDao.ping();
    }

    public void set(String key, String value) {
        lmRedisDao.set(key, value);
    }

    public String get(String key) {
        return lmRedisDao.get(key).toString();
    }

    public void setMap(String name, Map<String, Object> map, long expire) {
        lmRedisDao.setMap(name, map, expire);
    }

    public Object getMap(String name, String hashKey) {
        return lmRedisDao.getMap(name, hashKey);
    }

    public void deleteMap(String name, String... hashKeys) {
        lmRedisDao.deleteMap(name, hashKeys);
    }

    public void setObject(Object key, Object value, long expire) {
        lmRedisDao.setObject(key, value, expire);
    }

    public Object getObject(Object key) {
        return lmRedisDao.getObject(key);
    }

    public void deleteObject(Object key) {
        lmRedisDao.deleteObject(key);
    }

    public Object flushDB() {
        return lmRedisDao.flushDB();
    }

    public long sizeDB() {
        Object object = lmRedisDao.sizeDB();
        long result = Long.valueOf(object.toString());
        return result;
    }

    public boolean exists(final String key) {
        return lmRedisDao.exists(key);
    }

    /**
     * construction function
     */

    public LMRedisServiceImpl() {
    }

    /**
     * get and set function
     */

    public LMRedisDao getLmRedisDao() {
        return lmRedisDao;
    }

    public void setLmRedisDao(LMRedisDao lmRedisDao) {
        this.lmRedisDao = lmRedisDao;
    }
}
