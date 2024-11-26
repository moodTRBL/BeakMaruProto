package com.beakmaruproto.global

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono

@Controller
class TestController {
    @GetMapping(path = ["/me"])
    fun testWithWebSession(session: WebSession): Mono<String> {
        println(session.attributes["member"] as String?)
        return Mono.justOrEmpty<String>(session.getAttribute("member") as String?)
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