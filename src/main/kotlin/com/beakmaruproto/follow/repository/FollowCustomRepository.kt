package com.beakmaruproto.follow.repository

import com.beakmaruproto.follow.dto.FollowDTO
import reactor.core.publisher.Flux

interface FollowCustomRepository {
    suspend fun findFollowingsId(memberId: Long): Flux<FollowDTO>
}