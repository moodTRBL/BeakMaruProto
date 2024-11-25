package com.beakmaruproto.board

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Board(
    @Id
    val id: Long? = null,

    val title: String,

    val content: String,

    val isAnonymous: Boolean,

    val memberId: Long,

    @CreatedDate
    val createTime: LocalDateTime? = null,

    @LastModifiedDate
    var updateTime: LocalDateTime? = null,
) {
}