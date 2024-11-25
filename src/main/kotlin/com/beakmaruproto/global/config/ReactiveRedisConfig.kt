package com.beakmaruproto.global.config

import com.beakmaruproto.global.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class ReactiveRedisConfig @Autowired constructor(
    private val applicationProperties: ApplicationProperties
)  {
    val host = applicationProperties.data.redis.host
    val port = applicationProperties.data.redis.port

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun reactiveRedisTemplate(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Any> {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = GenericJackson2JsonRedisSerializer()
        val builder = RedisSerializationContext.newSerializationContext<String, Any>()
        val context = builder.key(keySerializer).value(valueSerializer).hashValue(valueSerializer).hashKey(keySerializer).build()
        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, context)
    }
}