package com.beakmaruproto.global.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring")
class ApplicationProperties(
    val data: Data
) {
    data class Data(
        val redis: Redis,
    ) {
        data class Redis(
            val host: String,
            val port: Int
        )
    }
}