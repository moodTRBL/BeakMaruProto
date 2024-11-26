package com.beakmaruproto.global.config

import com.beakmaruproto.global.SessionAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.WebFilter

@EnableWebFluxSecurity
@Configuration
class SecurityConfig {
    val permitAllList: MutableList<String> = mutableListOf(
        "/**",
    )

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .authorizeExchange { it
                .pathMatchers(*permitAllList.toTypedArray()).permitAll()
                .anyExchange().authenticated()
            }
            //.addFilterAt(SessionAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            //.addFilterBefore(SessionAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

//    @Bean
//    fun sessionAuthenticationFilter(): WebFilter {
//        return SessionAuthenticationFilter()
//    }
}