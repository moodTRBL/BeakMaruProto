package com.beakmaruproto.member

import com.beakmaruproto.logger
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.util.concurrent.ConcurrentHashMap

@Component
class SSESendProcessor {
    val sinks = ConcurrentHashMap<Long, Sinks.Many<ServerSentEvent<Any>>>()
    val log = logger()

    fun connect(memberId: Long): Flux<ServerSentEvent<Any>> {
        if(sinks.containsKey(memberId)) {
            return sinks[memberId]!!.asFlux()
        }
        sinks[memberId] = Sinks.many().multicast().onBackpressureBuffer()
        return sinks[memberId]!!.asFlux().doOnCancel {
            log.info("SSE Notification Cancelled by client: $memberId")
            finish(memberId)
        }
    }

    fun personalSend(memberId: Long, messageDTO: MessageDTO) {
        if(sinks.containsKey(memberId)) {
            sinks[memberId]!!.tryEmitNext(ServerSentEvent.builder<Any>()
                .event("message")
                .data(messageDTO)
                .id(messageDTO.messageId)
                .comment(messageDTO.content)
                .build()
            )
        }
    }

    fun groupSend(members: List<Long>, messageDTO: MessageDTO) {
        members.forEach { memberId->
            personalSend(memberId, messageDTO)
        }
    }

    fun successMessageSend(memberId: Long): Mono<Boolean> {
        return Mono.just(memberId)
            .flatMap { id->
                if(sinks.containsKey(id)) {
                    sinks[id]!!.tryEmitNext(ServerSentEvent.builder<Any>()
                        .event("config")
                        .data("connect success")
                        .comment("connect success")
                        .build()
                    )
                    Mono.just(true)
                } else {
                    Mono.error(Exception("존재하지 않는 SSE 체널"))
                }
             }
    }

    private fun finish(memberId: Long) {
        sinks[memberId]!!.tryEmitComplete()
        sinks.remove(memberId)
    }
}