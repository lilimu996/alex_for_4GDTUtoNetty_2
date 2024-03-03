package com.rxkj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    public static final Integer ONE_MINUTE = 60;

    public static final Integer ONE_HOUR = 60 * ONE_MINUTE;

    public static final Integer ONE_DAY = 24 * ONE_HOUR;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object object) {
        String json = JSON.toJSONString(object);
        stringRedisTemplate.opsForValue().set(key, json);
    }


    // 添加并设置过期时间
    public void set(String key, Object object, Integer expirationTime) {
        String json = JSON.toJSONString(object);
        stringRedisTemplate.opsForValue().set(key, json, expirationTime, TimeUnit.SECONDS);
    }

    public <T> T getObject(String key, Class<T> tClass) {
        String json = stringRedisTemplate.opsForValue().get(key);
        return JSON.parseObject(json, tClass);
    }

    public String get(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (Objects.isNull(value)) {
            return null;
        }
        value = value.replaceAll("^\"|\"$", "");
        return value;
    }

    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 示范：传入 example:
     */
    public void deleteRightLike(String key) {
        Set<String> keys = stringRedisTemplate.keys(key + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            stringRedisTemplate.delete(keys);
        }
    }

    public void deleteLeftLike(String key) {
        Set<String> keys = stringRedisTemplate.keys("*" + key);
        if (!CollectionUtils.isEmpty(keys)) {
            stringRedisTemplate.delete(keys);
        }
    }

    public void deleteLike(String key) {
        Set<String> keys = stringRedisTemplate.keys("*" + key + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            stringRedisTemplate.delete(keys);
        }
    }


    public <T> List<T> getList(String key) {
        String jsonValue = stringRedisTemplate.opsForValue().get(key);
        return JSON.parseObject(jsonValue, new TypeReference<List<T>>() {
        });
    }
}
