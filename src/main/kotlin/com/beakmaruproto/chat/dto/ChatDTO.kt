package com.beakmaruproto.chat.dto

import java.time.LocalDateTime

class ChatDTO(
    val senderId: Long,
    val receiverId: Long,
    val message: String,
    val roomId: Long,
    val sendTime: LocalDateTime,
)