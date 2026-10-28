package org.lhy.cachedemo.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheJsonCodec {
    private final ObjectMapper objectMapper;

    public CacheJsonCodec(){
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())//注册，使能支持jdk8的时间类的序列化和反序列化
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)//将时间戳格式转变为xxxx-xx-xx的形式
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    }
    //序列化
    public String encode(Object value){
        try {
            return objectMapper.writeValueAsString(value);
        }catch (JsonProcessingException e){
            throw new IllegalStateException("缓存序列化失败："+value.getClass().getName(),e);
        }
    }
    //反序列化
     //消除泛型擦除带来的影响 使用TypeReference
    public <T> T decode(String json , TypeReference<T> type){
        try {
            return objectMapper.readValue(json,type);
        }catch (JsonProcessingException e){
            log.warn("【缓存】反序列化失败，按未命中处理：{}",e.getOriginalMessage());
            return null;
        }
    }
}
