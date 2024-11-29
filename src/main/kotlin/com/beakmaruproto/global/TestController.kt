package com.beakmaruproto.global

import com.beakmaruproto.logger
import com.beakmaruproto.member.dto.MemberDTO
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono

@Controller
class TestController {

    private val log = logger()

    @GetMapping(path = ["/me"])
    @ResponseBody
    fun testWithWebSession(session: WebSession, exchange: ServerWebExchange): Mono<String> {
        log.info("session id : ${exchange.request.headers["X-AUTH-HEADER"]})}")
        log.info("member info : ${session.attributes["member"] as MemberDTO?}")
        return Mono.justOrEmpty<String>("")
            .switchIfEmpty(
                Mono.error(
                    ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "You don't have permission!"
                    )
                )
            )
    }

    @GetMapping("/websession")
    @ResponseBody
    fun getSession(session: WebSession): Mono<String?> {
        session.attributes["member"] = "hello"
        return Mono.just((session.attributes["member"] as String?)!!)
    }

//    @GetMapping(path = ["/me2"])
//    fun testWithAttribute(@SessionAttribute("user") user: UserInfo?): Mono<UserInfo> {
//        return Mono.justOrEmpty<Any>(user)
//            .switchIfEmpty(
//                Mono.error<Any>(
//                    ResponseStatusException(
//                        HttpStatus.UNAUTHORIZED,
//                        "You don't have permission!"
//                    )
//                )
//            )
//            .cast<E>(UserInfo::class.java)
//    }
}