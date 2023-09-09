package com.debezium.example.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PropertyCacheService {

    @Cacheable(value = "property", cacheManager = "cacheManager", key = "#key")
    public String cacheProperty(String key, String value) {
        return value;
    }

    @CacheEvict(value = "property", cacheManager = "cacheManager", key = "#key")
    public void cacheEvict(String key) {
    }

}
