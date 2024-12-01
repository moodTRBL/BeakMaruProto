package com.beakmaruproto.chat

import com.beakmaruproto.chat.dto.ChatDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("chat")
data class Chat (
    @Id
    val id: String? = null,

    val senderId: Long,

    val receiverId: Long,

    val roomId: Long,

    val message: String,

    val sendTime: LocalDateTime,
) {
    fun toDTO() = ChatDTO(
        senderId = senderId,
        receiverId = receiverId,
        message = message,
        roomId = roomId,
        sendTime = sendTime
    )
}