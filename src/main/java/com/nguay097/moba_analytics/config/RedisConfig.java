package com.nguay097.moba_analytics.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * Configuration for Spring Data Redis caching.
 *
 * This configuration class sets up Redis as the cache manager for the application,
 * with JSON serialization using a custom Jackson ObjectMapper and a 5-minute
 * time-to-live (TTL) for all cached entries. Polymorphic type information is
 * embedded in cached JSON using the @class property, allowing Spring to correctly
 * deserialize complex types including Java records and arrays on cache retrieval.
 *
 * @see RedisCacheManager
 * @see RedisCacheConfiguration
 */
@Configuration
public class RedisConfig {

    /**
     * Creates and configures a RedisCacheManager bean.
     *
     * Configures a custom ObjectMapper with polymorphic type validation to ensure
     * all cached objects — including Java records and arrays — are correctly
     * serialized and deserialized. Uses DefaultTyping.EVERYTHING to embed type
     * metadata for all types, and JsonTypeInfo.As.PROPERTY to store the type
     * as a @class field in the JSON. Sets a default TTL of 5 minutes for all
     * cache entries to prevent stale data from persisting in Redis.
     *
     * @param connectionFactory the Redis connection factory provided by Spring Data Redis
     * @return a configured RedisCacheManager instance ready for use throughout the application
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                        .allowIfBaseType(Object.class)
                        .build(),
                ObjectMapper.DefaultTyping.EVERYTHING,
                JsonTypeInfo.As.PROPERTY
        );

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer(mapper)
                        )
                );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}