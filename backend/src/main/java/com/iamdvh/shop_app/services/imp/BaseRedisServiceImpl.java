package com.iamdvh.shop_app.services.imp;

import com.iamdvh.shop_app.services.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class BaseRedisServiceImpl implements BaseRedisService {
  private final RedisTemplate<String, Object> redisTemplate;
  private final HashOperations<String, String, Object> hashOperations;

  public BaseRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void set(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void setTimeToLive(String key, long timeToLive) {
    try {
    redisTemplate.expire(key, timeToLive, TimeUnit.DAYS);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void hashSet(String key, String field, Object value) {
    try {
      hashOperations.put(key, field, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean hashExists(String key, String field) {
    return hashOperations.hasKey(key, field);
  }

  @Override
  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public Map<String, Object> getField(String key) {
    return hashOperations.entries(key);
  }

  @Override
  public Object hashGet(String key, String field) {
    return hashOperations.get(key, field);
  }

  @Override
  public List<Object> hashGetByPrefix(String key, String prefix) {
    List<Object> objects = new ArrayList<>();
    Map<String, Object> fields = hashOperations.entries(key);
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      if (entry.getKey().startsWith(prefix)) {
        objects.add(entry.getValue());
      }
    }

    return objects;
  }

  @Override
  public Set<String> getFieldPrefixes(String key) {
    return hashOperations.entries(key).keySet();
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public void delete(String key, String field) {
    hashOperations.delete(key, field);
  }

  @Override
  public void delete(String key, List<String> fields) {
    for (String field : fields) {
      hashOperations.delete(key, field);
    }
  }
}
