package com.beakmaruproto.repository

import com.beakmaruproto.member.Member
import com.beakmaruproto.member.repository.MemberRepository
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import kotlin.test.Test

@SpringBootTest
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate
) {

    @AfterEach
    fun afterEach() {
        r2dbcEntityTemplate.databaseClient.sql("SET FOREIGN_KEY_CHECKS=0")
        r2dbcEntityTemplate.databaseClient.sql("TRUNCATE TABLE member")
        r2dbcEntityTemplate.databaseClient.sql("SET FOREIGN_KEY_CHECKS=1")
    }

    @Test
    fun `member 저장`() {

    }
}