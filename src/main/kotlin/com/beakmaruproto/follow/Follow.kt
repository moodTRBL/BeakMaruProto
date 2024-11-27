package com.beakmaruproto.follow

import com.beakmaruproto.follow.dto.FollowDTO
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Follow(
    @Id
    val id: Long? = null,

    val fromId: Long,

    val toId: Long,

    @CreatedDate
    val createTime: LocalDateTime? = null,

    @LastModifiedDate
    var updateTime: LocalDateTime? = null,
) {
    fun toDto() = FollowDTO(
        followId = id!!,
        fromId = fromId,
        toId = toId,
    )
}