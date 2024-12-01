package com.beakmaruproto.chat

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class ChatRoom(
    @Id
    val id: Long? = null,

    val boardId: Long,

    @CreatedDate
    val createTime: LocalDateTime,
) {
}