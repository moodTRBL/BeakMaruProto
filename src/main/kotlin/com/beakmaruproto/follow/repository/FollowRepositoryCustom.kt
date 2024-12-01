package com.beakmaruproto.follow.repository

import com.beakmaruproto.follow.dto.FollowDTO
import reactor.core.publisher.Flux

interface FollowRepositoryCustom {
    suspend fun findFollowingsById(memberId: Long): List<FollowDTO>
    suspend fun deleteFollowing(memberId: Long, followingId: Long)
}