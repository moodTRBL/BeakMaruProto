package com.beakmaruproto.member.dto

data class MemberRequest(
    val username: String,
    val password: String,
    val email: String,
    val studentNumber: Int
) {
    fun toSaveDTO() = MemberSaveDTO(
        username = username,
        password = password,
        email = email,
        studentNumber = studentNumber
    )

    fun toUpdateDTO() = MemberUpdateDTO(
        username = username,
        password = password,
        email = email,
        studentNumber = studentNumber
    )
}