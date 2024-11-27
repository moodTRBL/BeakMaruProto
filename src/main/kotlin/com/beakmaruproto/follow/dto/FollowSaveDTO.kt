package com.beakmaruproto.follow.dto

import com.beakmaruproto.member.dto.MemberDTO

data class FollowSaveDTO(
    val fromId: Long,
    val toId: Long,
) {
    fun toMemberDTO() = MemberDTO(
        memberId = toId,
        username = "",
        password = "",
        email = "",
        studentNumber = 0
    )
}