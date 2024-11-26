package com.beakmaruproto.global

import com.beakmaruproto.logger
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class SessionAuthenticationFilter : WebFilter {

    private val log = logger()
    private val permitList: List<String> = listOf(
        "/member"
    )

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        log.info(
            "{} : {} {}",
            if (request.headers.getFirst("X-Forwarded-For") == null) request.remoteAddress else request.headers.getFirst(
                "X-Forwarded-For"
            ),
            request.method,
            request.uri.path
        )
        val list = permitList.filter { request.uri.path.contains(it) }
        if (list.isNotEmpty()) {
            return chain.filter(exchange)
        } else {
            return exchange.session
                .doOnNext { session->
                    log.info("${session.attributes["member"]}")
                    session.attributes["member"].let { if (it == null) throw Exception("인증되지 않은 사용자") }
                }.then(chain.filter(exchange))
        }
//        permitList.let { it ->
//            if (it.contains(request.uri.path)) {
//                return chain.filter(exchange)
//            } else {
//                return exchange.session
//                    .doOnNext { session->
//                        session.attributes["member"].let { if (it == null) throw Exception("인증되지 않은 사용자") }
//                    }.then(chain.filter(exchange))
//            }
//        }
    }
}