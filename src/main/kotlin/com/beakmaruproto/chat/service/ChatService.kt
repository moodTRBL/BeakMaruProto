package com.beakmaruproto.chat.service

import com.beakmaruproto.chat.dto.ChatDTO
import com.beakmaruproto.chat.dto.ChatSaveDTO
import com.beakmaruproto.chat.dto.ChatSendDTO
import com.beakmaruproto.chat.repository.ChatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ChatService @Autowired constructor(
    private val chatRepository: ChatRepository,
) {
    fun saveChat(chatSaveDTO: ChatSaveDTO): Mono<ChatDTO> {
        return chatRepository.save(chatSaveDTO.toEntity())
            .map { chat -> chat.toDTO() }
    }

    fun personalSend(chatSendDTO: ChatSendDTO): Flux<ChatDTO> {
        return chatRepository.findBySender(chatSendDTO.senderId, chatSendDTO.receiverId)
            .map { chat -> chat.toDTO() }
    }

    fun listenMessage() {
        //chatRepository.listenSendMessage() 호출하여 듣기
        //새로운 채팅방이면 채팅방 생성
        //해당 채팅방 듣기
    }
}