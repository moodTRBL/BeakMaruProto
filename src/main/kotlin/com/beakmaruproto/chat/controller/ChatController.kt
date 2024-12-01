package com.beakmaruproto.chat.controller

import com.beakmaruproto.chat.dto.ChatDTO
import com.beakmaruproto.chat.dto.ChatRequest
import com.beakmaruproto.chat.dto.ChatSendDTO
import com.beakmaruproto.chat.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RequestMapping("/test/chat")
@Controller
class ChatController @Autowired constructor(
    private val chatService: ChatService,
) {

    @PostMapping("/save")
    @ResponseBody
    fun saveChat(@RequestBody chatRequest: ChatRequest): Mono<ResponseEntity<ChatDTO>> {
        return chatService.saveChat(chatRequest.toSaveDTO())
            .map { dto -> ResponseEntity.ok(dto) }
    }

    @GetMapping("/sender/{senderId}/receiver/{receiverId}", produces= [MediaType.TEXT_EVENT_STREAM_VALUE])
    @ResponseBody
    fun sendPersonal(
        @PathVariable senderId: Long,
        @PathVariable receiverId: Long
    ): Flux<ChatDTO> {
        return chatService.personalSend(ChatSendDTO(senderId, receiverId, 1))
            .subscribeOn(Schedulers.boundedElastic())
    }
}