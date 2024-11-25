package com.beakmaruproto.global

import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class SessionAuthenticationFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        return exchange.session
            .doOnNext { session->
                session.attributes["member"].let { if (it == null) throw Exception("인증되지 않은 사용자") }
            }.then(chain.filter(exchange))
    }
}