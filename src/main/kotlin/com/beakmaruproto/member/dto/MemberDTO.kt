package com.beakmaruproto.member.dto

import com.beakmaruproto.member.Member
import java.io.Serializable
import java.time.LocalDateTime

data class MemberDTO(
    val memberId: Long = 0,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val nickname: String = "",
    val studentNumber: Int = 0,
) {
    fun toSaveResponse() = MemberResponse(
        nickname = nickname,
        email = email,
    )

    companion object {
        private const val serialVersionUID = 1L
    }
}