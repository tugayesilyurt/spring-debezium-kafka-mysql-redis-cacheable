package com.debezium.example.service;

import com.debezium.example.entity.Property;
import com.debezium.example.repository.PropertyRepository;
import com.debezium.example.request.PropertyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Transactional
    public String createProperty(String key,String value){
        Property property = Property.builder()
                .propertyKey(key)
                .propertyValue(value).build();
        return propertyRepository.save(property).getPropertyValue();
    }

    @Transactional
    public void updateProperty(PropertyRequest propertyRequest){
        Property property = propertyRepository.findByPropertyKey(propertyRequest.getPropertyKey())
                .orElseThrow(() -> new RuntimeException("Property Not Found!"));

        property.setPropertyValue(propertyRequest.getPropertyValue());
        propertyRepository.save(property);
    }

    @Transactional
    @Cacheable(value = "property", cacheManager = "cacheManager", key = "#key")
    public String getProperty(String key) throws InterruptedException {
        Property property = propertyRepository.findByPropertyKey(key)
                .orElseThrow(() -> new RuntimeException("Property Not Found!"));

        Thread.sleep(1000);

        return property.getPropertyValue();
    }


}
