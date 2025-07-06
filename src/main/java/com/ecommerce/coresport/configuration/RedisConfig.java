package com.ecommerce.coresport.configuration;

import com.ecommerce.coresport.model.RefreshTokenSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, RefreshTokenSession> redisTemplate(
            RedisConnectionFactory cf) {
        RedisTemplate<String, RefreshTokenSession> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);

        // Key serializers
        var keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);

        // Configure ObjectMapper for Instant serialization
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Use constructor to avoid deprecated setObjectMapper()
        GenericJackson2JsonRedisSerializer valueSer =
                new GenericJackson2JsonRedisSerializer(mapper);

        template.setValueSerializer(valueSer);
        template.setHashValueSerializer(valueSer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory cf) {
        StringRedisTemplate template = new StringRedisTemplate(cf);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
