package com.redis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * 提供各种 Redis 操作方法
 */
@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 设置字符串值
     */
    public void set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            logger.debug("Set Redis key {}: {}", key, value);
        } catch (Exception e) {
            logger.error("Failed to set Redis key {}", key, e);
        }
    }

    /**
     * 设置字符串值并指定过期时间
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
            logger.debug("Set Redis key {}: {} ({} {})", key, value, timeout, unit);
        } catch (Exception e) {
            logger.error("Failed to set Redis key {}", key, e);
        }
    }

    /**
     * 获取字符串值
     */
    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("Failed to get Redis key {}", key, e);
            return null;
        }
    }

    /**
     * 删除键
     */
    public Boolean delete(String key) {
        try {
            return stringRedisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("Failed to delete Redis key {}", key, e);
            return false;
        }
    }

    /**
     * 判断键是否存在
     */
    public Boolean hasKey(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("Failed to check Redis key exists {}", key, e);
            return false;
        }
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            return stringRedisTemplate.expire(key, timeout, unit);
        } catch (Exception e) {
            logger.error("Failed to set Redis key {} expiration", key, e);
            return false;
        }
    }

    /**
     * 获取过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        try {
            return stringRedisTemplate.getExpire(key, unit);
        } catch (Exception e) {
            logger.error("Failed to get Redis key {} expiration", key, e);
            return null;
        }
    }

    /**
     * 自增
     */
    public Long increment(String key) {
        try {
            return stringRedisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            logger.error("Failed to increment Redis key {}", key, e);
            return null;
        }
    }

    /**
     * 自增指定值
     */
    public Long increment(String key, long delta) {
        try {
            return stringRedisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            logger.error("Failed to increment Redis key {}", key, e);
            return null;
        }
    }

    /**
     * 自减
     */
    public Long decrement(String key) {
        try {
            return stringRedisTemplate.opsForValue().decrement(key);
        } catch (Exception e) {
            logger.error("Failed to decrement Redis key {}", key, e);
            return null;
        }
    }

    /**
     * 自减指定值
     */
    public Long decrement(String key, long delta) {
        try {
            return stringRedisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            logger.error("Failed to decrement Redis key {}", key, e);
            return null;
        }
    }

    /**
     * 设置对象值
     */
    public void setObject(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            logger.debug("Set Redis object key {}: {}", key, value);
        } catch (Exception e) {
            logger.error("Failed to set Redis object key {}", key, e);
        }
    }

    /**
     * 设置对象值并指定过期时间
     */
    public void setObject(String key, Object value, long timeout, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
            logger.debug("Set Redis object key {}: {} ({} {})", key, value, timeout, unit);
        } catch (Exception e) {
            logger.error("Failed to set Redis object key {}", key, e);
        }
    }

    /**
     * 获取对象值
     */
    public Object getObject(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("Failed to get Redis object key {}", key, e);
            return null;
        }
    }

    /**
     * 获取值操作对象
     */
    public ValueOperations<String, String> getValueOperations() {
        return stringRedisTemplate.opsForValue();
    }

    /**
     * 获取对象值操作对象
     */
    public ValueOperations<Object, Object> getObjectValueOperations() {
        return redisTemplate.opsForValue();
    }

    /**
     * 获取字符串 Redis 模板
     */
    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    /**
     * 获取 Redis 模板
     */
    public RedisTemplate<Object, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 分布式锁（简单实现）
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime) {
        try {
            return stringRedisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("Failed to acquire Redis lock {}", lockKey, e);
            return false;
        }
    }

    /**
     * 释放分布式锁（简单实现）
     */
    public boolean unlock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        try {
            Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                    Collections.singletonList(lockKey), requestId);
            return result != null && result == 1L;
        } catch (Exception e) {
            logger.error("Failed to release Redis lock {}", lockKey, e);
            return false;
        }
    }
}
