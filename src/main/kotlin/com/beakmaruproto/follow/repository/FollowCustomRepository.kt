package com.beakmaruproto.follow.repository

import com.beakmaruproto.follow.dto.FollowDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FollowCustomRepository {
    suspend fun findFollowingsId(memberId: Long): Flux<FollowDTO>
    suspend fun deleteFollowing(memberId: Long, followingId: Long)
}