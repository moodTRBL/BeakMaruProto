package com.beakmaruproto.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.web.server.session.HeaderWebSessionIdResolver
import org.springframework.web.server.session.WebSessionIdResolver

@Configuration
class SessionConfig {

    @Bean
    fun webSessionIdResolver(): WebSessionIdResolver {
        val sessionIdResolver = HeaderWebSessionIdResolver()
        sessionIdResolver.headerName = "X-AUTH-HEADER"
        return sessionIdResolver
    }

    @Bean
    fun springSessionDefaultRedisSerializer(): GenericJackson2JsonRedisSerializer {
        return GenericJackson2JsonRedisSerializer()
    }
}