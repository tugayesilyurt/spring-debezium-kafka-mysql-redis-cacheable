package com.debezium.example.service;

import com.debezium.example.request.PropertyListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebeziumListener {

    private final PropertyCacheService propertyCacheService;

    @KafkaListener(topics = "property.debezium.debezium_property", containerFactory = "kafkaListenerDebezium", groupId = "kafkaListenerDebezium")
    public void debeziumListener(@Payload(required = false) PropertyListener message) {
        try {
            if (message.getPayload().getOp().equals("c")) {
                propertyCacheService.cacheProperty(message.getPayload().getAfter().getProperty_key(),
                        message.getPayload().getAfter().getProperty_value());
            } else if (message.getPayload().getOp().equals("u")) {
                propertyCacheService.cacheEvict(message.getPayload().getBefore().getProperty_key());
                propertyCacheService.cacheProperty(message.getPayload().getAfter().getProperty_key(),
                        message.getPayload().getAfter().getProperty_value());
            } else{
                // do nothing
            }
            log.info(message.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
