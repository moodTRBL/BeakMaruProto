package com.beakmaruproto.member

import com.beakmaruproto.member.dto.MemberDTO
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Table
data class Member(
    @Id
    val id: Long? = null,

    var username: String,

    var password: String,

    var email: String,

    var studentNumber: Int,

    @CreatedDate
    val createTime: LocalDateTime? = null,

    @LastModifiedDate
    var updateTime: LocalDateTime? = null,
) {
    fun toDto() = Mono.just(
        MemberDTO(
        memberId = id!!,
        username = username,
        password = password,
        email = email,
        studentNumber = studentNumber,
    )
    )
}