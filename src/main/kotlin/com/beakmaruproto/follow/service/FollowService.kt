package com.beakmaruproto.follow.service

import com.beakmaruproto.follow.dto.FollowSaveDTO
import com.beakmaruproto.follow.dto.FollowDeleteDTO
import com.beakmaruproto.member.dto.MemberDTO
import reactor.core.publisher.Mono

interface FollowService {
    suspend fun follow(followSaveDTO: FollowSaveDTO): Mono<MemberDTO>
    suspend fun unfollow(unFollowDTO: FollowDeleteDTO)
}