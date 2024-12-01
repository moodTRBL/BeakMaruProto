package com.beakmaruproto.chat.dto

data class ChatRequest(
    val senderId: Long,
    val receiverId: Long,
    val roomId: Long,
    val message: String,
) {
    fun toSaveDTO() = ChatSaveDTO(
        senderId = senderId,
        receiverId = receiverId,
        message = message,
        roomId = roomId
    )
}