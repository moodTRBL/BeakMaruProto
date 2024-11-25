package com.beakmaruproto.member

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.ResponseBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

@Controller
class NotificationController @Autowired constructor(
    private val sseSendProcessor: SSESendProcessor
) {
    @GetMapping(value = ["/sse"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    @ResponseBody
    fun seeConnect(@RequestHeader("memberId") memberId: Long): Flux<ServerSentEvent<Any>> {
        return sseSendProcessor.connect(memberId)
    }

    @GetMapping("/sse/connect")
    @ResponseBody
    fun sseSuccessConnect(@RequestHeader("memberId") memberId: Long): Mono<ResponseEntity<Boolean>> {
        return sseSendProcessor.successMessageSend(memberId)
            .map { it -> ResponseEntity.ok(it) }
    }

    private val eventId: AtomicInteger = AtomicInteger(1)

    @GetMapping(value = ["/sse/test"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    @ResponseBody
    fun test(): Flux<ServerSentEvent<String>> {
        return Flux.interval(Duration.ofMillis(500))
            .map { message->
                val id = eventId.incrementAndGet().toString()
                ServerSentEvent.builder<String>("message $message")
                    .event("notification")
                    .id(id)
                    .comment("test notice")
                    .build()
            }
    }
}