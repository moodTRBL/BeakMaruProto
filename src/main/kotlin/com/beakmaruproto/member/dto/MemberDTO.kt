package com.beakmaruproto.member.dto

import java.time.LocalDateTime

data class MemberDTO(
    val memberId: Long,
    val username: String,
    val password: String,
    val email: String,
    val studentNumber: Int,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime
) {
    fun toSaveResponse() = MemberResponse(
        username = username,
        email = email,
    )
}