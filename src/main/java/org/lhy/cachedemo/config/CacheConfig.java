package org.lhy.cachedemo.config;


import lombok.extern.slf4j.Slf4j;
import org.lhy.cachedemo.cache.CacheInvalidationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
public class CacheConfig {
    //缓存失效广播监听容器
    //发布订阅（pub/sub）的监听容器  用来做多实例缓存同步
    @Bean
    public RedisMessageListenerContainer cacheInvalidationContainer(
            RedisConnectionFactory connectionFactory,
            CacheInvalidationListener listener,
            CacheProperties properties) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener) listener,
                new ChannelTopic(properties.getInvalidationChannel()));
        log.info("[缓存] 失效广播订阅已注册：channel={}", properties.getInvalidationChannel());
        return container;
    }


    //ScheduledExecutorService 定时线程池
    //专门给缓存过期  缓存定时清理任务用的单线程池
    @Bean(destroyMethod = "shutdownNow")
    public ScheduledExecutorService cacheEvictionScheduler() {
        AtomicInteger seq = new AtomicInteger();
        //
        return Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "tomshop-cache-evict-" + seq.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        });
    }
}