package org.lhy.cachedemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tomshop.cache")
public class CacheProperties {
    private boolean enabled = true;
    private String keyPrefix = "tomshop:cache:v1";
    private String invalidationChannel = "tomshop:cache:invalidation";
    private long localMaximumSize =100000;
    private long localTtlSeconds = 300;
    private long remoteTtlSeconds = 300;
    private long mullTtlSeconds =30;
    private long lockWaitSeconds = 1;
    private long lockLeaseSeconds = 10;
    private double ttlJitterRatio =0.1;
    private long delayedEvictMillis = 500;
    private long remoteRetryBackoffMillis =5000;
}
