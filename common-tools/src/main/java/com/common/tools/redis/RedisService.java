package com.common.tools.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis服务类
 */
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate,
                       StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 设置字符串值
     * @param key 键
     * @param value 值
     */
    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置字符串值（带过期时间）
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void setString(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取字符串值
     * @param key 键
     * @return 值
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除键
     * @param key 键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 判断键是否存在
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    /**
     * 设置对象值
     * @param key 键
     * @param value 对象
     */
    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置对象值（带过期时间）
     * @param key 键
     * @param value 对象
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void setObject(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取对象值
     * @param key 键
     * @param clazz 对象类型
     * @param <T> 泛型类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void expire(String key, long timeout, TimeUnit unit) {
        stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取剩余过期时间
     * @param key 键
     * @param unit 时间单位
     * @return 剩余时间
     */
    public long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }
}
