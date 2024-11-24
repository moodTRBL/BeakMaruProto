package com.beakmaruproto.service

import com.beakmaruproto.member.Member
import com.beakmaruproto.member.repository.MemberRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.test.StepVerifier

@SpringBootTest
class MemberServiceImplTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate
) : FunSpec({
    afterTest {
        r2dbcEntityTemplate.databaseClient.sql("SET FOREIGN_KEY_CHECKS=0").fetch().rowsUpdated().block()
        r2dbcEntityTemplate.databaseClient.sql("TRUNCATE TABLE member").fetch().rowsUpdated().block()
        r2dbcEntityTemplate.databaseClient.sql("SET FOREIGN_KEY_CHECKS=1").fetch().rowsUpdated().block()
    }

    test("member 저장") {
        val member = Member(
            username = "username",
            password = "password",
            email = "email",
            studentNumber = 20221482
        )
        StepVerifier
            .create(mono { memberRepository.save(member) })
            .consumeNextWith { it->
                it.username shouldBe member.username
                it.password shouldBe member.password
                it.email shouldBe member.email
                it.studentNumber shouldBe member.studentNumber
            }
            .expectComplete()
            .verify()

        StepVerifier
            .create(memberRepository.findByUsername(member.username))
            .consumeNextWith { it->
                it.username shouldBe member.username
                it.password shouldBe member.password
                it.email shouldBe member.email
                it.studentNumber shouldBe member.studentNumber
            }
    }

    test("member 수정") {
        val member = Member(
            username = "username",
            password = "password",
            email = "email",
            studentNumber = 20221482
        )
        val savedMember = memberRepository.save(member)
        savedMember.username = "update"
        savedMember.password = "update"
        savedMember.email = "update"
        savedMember.studentNumber = 20201482

        StepVerifier
            .create(mono { memberRepository.save(savedMember) })
            .consumeNextWith { it->
                it.username shouldBe savedMember.username
                it.password shouldBe savedMember.password
                it.email shouldBe savedMember.email
                it.studentNumber shouldBe savedMember.studentNumber
            }
            .expectComplete()
            .verify()
    }
})