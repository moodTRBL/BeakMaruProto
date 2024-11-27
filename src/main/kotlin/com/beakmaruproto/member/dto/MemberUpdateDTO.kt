package com.beakmaruproto.member.dto

import com.beakmaruproto.member.Member
import reactor.core.publisher.Mono

data class MemberUpdateDTO(
    val originUsername: String,
    val username: String,
    val password: String,
    val email: String,
    val nickname: String,
    val studentNumber: Int
) {
    fun toUpdateEntity(member: Member): Mono<Member> {
        member.username = username
        member.password = password
        member.email = email
        member.nickname = nickname
        member.studentNumber = studentNumber
        return Mono.just(member)
    }
}