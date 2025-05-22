package markigor.io.newscrawler.application.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.service.RedisCacheService;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements RedisCacheService {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    @ValidCheck
    public synchronized <T> T getValue(String key, Class<T> clazz) {
        Object obj = redisTemplate.opsForValue().get(key);

        if (Objects.isNull(obj)) {
            return null;
        }

        return objectMapper.convertValue(obj, clazz);
    }

    @Override
    @ValidCheck
    public <T> T getValue(String key, TypeReference<T> typeReference) {
        Object obj = redisTemplate.opsForValue().get(key);

        if (Objects.isNull(obj)) {
            return null;
        }

        return objectMapper.convertValue(obj, typeReference);
    }

    @Override
    @ValidCheck
    public String getValue(String key) {
        Object obj = redisTemplate.opsForValue().get(key);

        if (Objects.isNull(obj)) {
            return null;
        }

        return obj.toString();
    }

    @Override
    @ValidCheck
    public <T> T getAllValue(List<String> keys, TypeReference<T> typeReference) {
        List<Object> obj = redisTemplate.opsForValue().multiGet(keys);

        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }

        return objectMapper.convertValue(obj, typeReference);
    }

    @Override
    @ValidCheck
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    @ValidCheck
    public void setValue(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value);

        this.setExpire(key, timeout, timeUnit);
    }

    @Override
    @ValidCheck
    public void setValue(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value);

        this.setExpire(key, timeout, timeUnit);
    }

    @Override
    @ValidCheck
    public void updateValue(String key, Object value) {
        Object obj = redisTemplate.opsForValue().get(key);

        if (!Objects.isNull(obj)) {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    @Override
    @ValidCheck
    public void updateValue(String key, String value) {
        Object obj = redisTemplate.opsForValue().get(key);

        if (Objects.isNull(obj))
            return;

        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    @ValidCheck
    public void setExpire(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    @ValidCheck
    public void removeValue(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void leftPushList(String key, Object object, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForList().leftPush(key, object);
        redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public <T> List<T> getAllList(String key, Class<T> clazz) {
        List<Object> list = redisTemplate.opsForList().range(key, 0, -1);
        return objectMapper.convertValue(list, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
    }
}
