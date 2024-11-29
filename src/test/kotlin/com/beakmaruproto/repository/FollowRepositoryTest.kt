package com.beakmaruproto.repository

import com.beakmaruproto.follow.Follow
import com.beakmaruproto.follow.repository.FollowRepository
import com.beakmaruproto.member.Member
import com.beakmaruproto.member.dto.MemberDTO
import com.beakmaruproto.member.repository.MemberRepository
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.test.StepVerifier

@SpringBootTest
class FollowRepositoryTest(
    private val followRepository: FollowRepository,
    private val memberRepository: MemberRepository,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate
) : FunSpec({
    afterTest {
        r2dbcEntityTemplate.databaseClient.sql("SET FOREIGN_KEY_CHECKS=0").fetch().rowsUpdated().block()
        r2dbcEntityTemplate.databaseClient.sql("TRUNCATE TABLE member").fetch().rowsUpdated().block()
        r2dbcEntityTemplate.databaseClient.sql("TRUNCATE TABLE follow").fetch().rowsUpdated().block()
        r2dbcEntityTemplate.databaseClient.sql("SET FOREIGN_KEY_CHECKS=1").fetch().rowsUpdated().block()
    }

    test("follow하기") {
        val members = mutableListOf<Member>()
        for (i in 1..5) {
            val member = memberRepository.save(Member(
                username = "username$i",
                password = "password$i",
                email = "email$i",
                nickname = "nickname$i",
                studentNumber = i
            ))
            members.add(member)
        }
        for (i in 0..3) {
            val follow = followRepository.save(Follow(
                fromId = members[i].id!!,
                toId = members[i + 1].id!!,
            ))
        }

        for (member in members) {
            val list = memberRepository.findFollowers(member.id!!)
            list.doOnNext { follow ->
                println("my id: ${member.id}, follower id: ${follow.memberId}")
                follow.memberId shouldBe (member.id!! + 1)
            }
        }
        for (i in 1..4) {
            val list = memberRepository.findFollowings(members[i].id!!)
            list.doOnNext { follow ->
                println("my id: ${members[i].id}, following id: ${follow.memberId}")
                follow.memberId shouldBe (members[i].id!! - 1)
            }
        }
    }

    //자신의 팔로잉 상대를 모두찾는다
    //팔로우 아이디를 찾는다
    //해당 팔로우 모두 삭제
    test("unfollow 하기") {
        val members = mutableListOf<Member>()
        for (i in 1..5) {
            val member = memberRepository.save(Member(
                username = "username$i",
                password = "password$i",
                email = "email$i",
                nickname = "nickname$i",
                studentNumber = i
            ))
            members.add(member)
        }
        for (i in 1..4) {
            val follow = followRepository.save(Follow(
                fromId = members[0].id!!,
                toId = members[i].id!!,
            ))
        }
        runBlocking {
            followRepository.findAll().toList().size shouldBe 4
            println("${members[0].id}, ${members[1].id}")
            followRepository.deleteFollowing(members[0].id!!, members[1].id!!)
            followRepository.deleteFollowing(members[0].id!!, members[2].id!!)
        }
        followRepository.findAll().toList().size shouldBe 3
    }
})