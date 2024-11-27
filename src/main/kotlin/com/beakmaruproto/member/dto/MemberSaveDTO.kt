package com.beakmaruproto.member.dto

import com.beakmaruproto.member.Member

data class MemberSaveDTO(
    val username: String,
    val password: String,
    val email: String,
    val nickname: String,
    val studentNumber: Int
) {
    fun toEntity() = Member(
        username = username,
        password = password,
        email = email,
        nickname = nickname,
        studentNumber = studentNumber
    )
}