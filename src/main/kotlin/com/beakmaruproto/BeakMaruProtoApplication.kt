package com.beakmaruproto

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession


@EnableRedisRepositories
@EnableRedisWebSession(maxInactiveIntervalInSeconds = 60*60*2)
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@ConfigurationPropertiesScan
@SpringBootApplication
class BeakMaruProtoApplication

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

fun main(args: Array<String>) {
    runApplication<BeakMaruProtoApplication>(*args)
}
