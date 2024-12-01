package com.beakmaruproto.repository

import com.beakmaruproto.follow.Follow
import com.beakmaruproto.follow.repository.FollowRepository
import com.beakmaruproto.follow.repository.FollowRepositoryCustom
import com.beakmaruproto.member.Member
import com.beakmaruproto.member.repository.MemberRepository
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.kotlin.test.test
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
            memberRepository.findFollowers(member.id!!)
                .forEach { follower->
                    println("my id: ${member.id}, following id: ${follower.memberId}")
                    follower.memberId shouldBe (member.id!! + 1)
                }
        }
        for (i in 1..4) {
            memberRepository.findFollowings(members[i].id!!)
                .forEach { follower->
                    println("my id: ${members[i].id}, following id: ${follower.memberId}")
                    follower.memberId shouldBe (members[i].id!! - 1)
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
            followRepository.save(Follow(
                fromId = members[0].id!!,
                toId = members[i].id!!,
            ))
        }
        followRepository.findAll().toList().size shouldBe 4
        followRepository.deleteFollowing(members[0].id!!, members[1].id!!)
        followRepository.deleteFollowing(members[0].id!!, members[2].id!!)
        followRepository.findAll().toList().size shouldBe 2
    }
})