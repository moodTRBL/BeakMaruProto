package com.beakmaruproto.member.dto

data class MemberRequest(
    val originUsername: String,
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
        originUsername = originUsername,
        username = username,
        password = password,
        email = email,
        studentNumber = studentNumber
    )
}