# Project Name

spring-debezium-kafka-mysql-redis-cacheable

## Tech Stack

- Java
- Spring Boot
- MySQL
- Apache Kafka
- Redis
- Debezium

## Description

Spring Boot - Debezium - Kafka - MySQL - Redis - Cacheable ( all in one example )

## Architecture

![System Design](https://github.com/tugayesilyurt/spring-debezium-kafka-mysql-redis-cacheable/blob/main/assets/SystemDesign.png)

## Installation

```shell
# Clone the repository
git clone https://github.com/tugayesilyurt/spring-debezium-kafka-mysql-redis-cacheable.git

# Change directory
cd spring-debezium-kafka-mysql-redis-cacheable

# Install dependencies
docker-compose up -d
```

- `Debezium Connector`

```java
curl --location 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data '{
    "name": "property-connector",
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector",
        "database.allowPublicKeyRetrieval":"true",
        "database.hostname": "host.docker.internal",
        "database.port": "3306",
        "database.user": "debezium",
        "database.password": "123456",
        "database.include.list": "debezium",
        "table.include.list": "debezium.debezium_property",
        "topic.prefix": "property",
        "schema.history.internal.kafka.bootstrap.servers":  "kafka:9092",
        "schema.history.internal.kafka.topic": "schema-changes.db",
        "database.server.id": 1
    }
}'
```

- `Redis CacheManager`

```java
    @Bean(value = "cacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> API_PREFIX.concat(SEPARATOR)
                        .concat(cacheName).concat(SEPARATOR))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        redisCacheConfiguration.usePrefix();

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }
```

- `Cacheable`

```java
    @Cacheable(value = "property", cacheManager = "cacheManager", key = "#key")
    public String cacheProperty(String key, String value) {
        return value;
    }

    @CacheEvict(value = "property", cacheManager = "cacheManager", key = "#key")
    public void cacheEvict(String key) {
    }
```


