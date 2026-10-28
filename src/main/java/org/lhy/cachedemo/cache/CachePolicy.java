package org.lhy.cachedemo.cache;

public record CachePolicy(long localTtlSeconds,long remoteTtlSeconds) {

    //紧凑构造器 判断入参
    public CachePolicy{
        if (localTtlSeconds<=0||remoteTtlSeconds<=0){
            throw new IllegalArgumentException("缓存TTL必须为正数");
        }
    }
   //静态工厂方法，不使用new方法来调用 ，同时 使用时也会判断入参
    public static CachePolicy of(long localTtlSeconds,long remoteTtlSeconds){
        return new CachePolicy(localTtlSeconds,remoteTtlSeconds);
    }
}
