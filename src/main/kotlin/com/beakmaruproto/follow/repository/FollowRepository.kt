package com.beakmaruproto.follow.repository

import com.beakmaruproto.follow.Follow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository : CoroutineCrudRepository<Follow, Long>, FollowCustomRepository {
}