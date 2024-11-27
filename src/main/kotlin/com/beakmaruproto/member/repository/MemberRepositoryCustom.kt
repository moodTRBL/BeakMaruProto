package com.beakmaruproto.member.repository

import com.beakmaruproto.member.dto.MemberDTO
import reactor.core.publisher.Flux

interface MemberRepositoryCustom {
    suspend fun findFollowers(memberId: Long): Flux<MemberDTO>
    suspend fun findFollowings(memberId: Long): Flux<MemberDTO>
}