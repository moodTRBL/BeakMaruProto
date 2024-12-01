package com.beakmaruproto.chat.dto

data class ChatSendDTO(
    val senderId: Long,
    val receiverId: Long,
    val roomId: Long,
)