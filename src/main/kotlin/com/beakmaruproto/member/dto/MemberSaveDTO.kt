package com.beakmaruproto.member.dto

import com.beakmaruproto.member.Member

data class MemberSaveDTO(
    val username: String,
    val password: String,
    val email: String,
    val studentNumber: Int
) {
    fun toEntity() = Member(
        username = username,
        password = password,
        email = email,
        studentNumber = studentNumber
    )
}