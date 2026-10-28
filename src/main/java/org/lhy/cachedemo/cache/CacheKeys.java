package org.lhy.cachedemo.cache;


public final class CacheKeys {
    private CacheKeys(){}

    public static final String CATEGORY_TREE = "category:tree";

    public static String productDetail(Long spuId){
        return "product:detail:"+spuId;
    }
}
