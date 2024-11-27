package com.beakmaruproto.member.dto

data class MemberLoginRequest(
    val username: String,
    val password: String
) {
    fun toLoginDTO() = MemberLoginDTO(username, password)
}