package com.beakmaruproto.follow.repository

import com.beakmaruproto.follow.Follow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FollowRepository : CoroutineCrudRepository<Follow, Long>, FollowRepositoryCustom {
}