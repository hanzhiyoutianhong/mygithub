package cc.linkedme.dao.impl;

import cc.linkedme.dao.LMRedisDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by qipo on 15/9/5.
 */
public class LMRedisDaoImpl implements LMRedisDao {

    private static final String REDISCODE = "utf-8";

    private RedisTemplate redisTemplate;

    public Object del(final String... keys) {
        return redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }

    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    public Object get(final String key) {
        return redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return new String(connection.get(key.getBytes()), REDISCODE);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }

    public Set Setkeys(String pattern) {
        return redisTemplate.keys(pattern);

    }

    public boolean exists(String key) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.hasKey(key);
        } else {
            return false;
        }
    }


    public void setMap(String name, Map<String, Object> map, final long expire) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(name, map);
        if (expire > 0) {
            redisTemplate.expire(name, expire, TimeUnit.SECONDS);
        }
    }

    public Object getMap(String name, String hashKey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(name, hashKey);
    }

    public void deleteMap(String name, String... hashKeys) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        for (int i = 0; i < hashKeys.length; i++) {
            hashOperations.delete(name, hashKeys[i]);
        }
    }

    public void setObject(Object key, Object value, final long expire) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        if (expire > 0) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public Object getObject(Object key) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void deleteObject(Object key) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.getOperations().delete(key);
    }

    public Object flushDB() {
        return redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    public Object sizeDB() {
        return redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    public Object ping() {
        return redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    /**
     * get and set function
     */

    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
