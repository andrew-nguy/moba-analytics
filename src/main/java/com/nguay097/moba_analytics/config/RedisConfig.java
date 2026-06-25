package com.nguay097.moba_analytics.config;

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
 * with JSON serialization using Jackson and a 5-minute time-to-live (TTL) for all
 * cached entries. All DTOs and cached objects are serialized to JSON format for
 * efficient storage and retrieval.
 *
 * @see RedisCacheManager
 * @see RedisCacheConfiguration
 */
@Configuration
public class RedisConfig {

    /**
     * Creates and configures a RedisCacheManager bean.
     *
     * Configures the cache manager to use JSON serialization (GenericJackson2JsonRedisSerializer)
     * for cache values and sets a default TTL of 5 minutes for all cache entries.
     * This ensures cached data automatically expires, preventing stale data from persisting
     * in Redis.
     *
     * @param connectionFactory the Redis connection factory provided by Spring Data Redis
     * @return a configured RedisCacheManager instance ready for use throughout the application
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()
                        )
                );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}