package com.beakmaruproto.chat.dto

import com.beakmaruproto.chat.Chat
import java.time.LocalDateTime

data class ChatSaveDTO(
    val senderId: Long,
    val receiverId: Long,
    val roomId: Long,
    val message: String,
) {
    fun toEntity() = Chat(
        senderId = senderId,
        receiverId = receiverId,
        message = message,
        roomId = roomId,
        sendTime = LocalDateTime.now()
    )
}