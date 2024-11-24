package com.beakmaruproto.member.repository

import com.beakmaruproto.member.Member
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface MemberRepository : CoroutineCrudRepository<Member, Long> {
    fun findByUsername(username: String): Mono<Member>
    fun existsByUsername(username: String): Mono<Boolean>
}