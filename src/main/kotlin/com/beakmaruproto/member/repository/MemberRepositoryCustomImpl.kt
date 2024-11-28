package com.beakmaruproto.member.repository

import com.beakmaruproto.member.dto.MemberDTO
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class MemberRepositoryCustomImpl @Autowired constructor(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val r2dbcConverter: MappingR2dbcConverter
) : MemberRepositoryCustom {
    override suspend fun findFollowers(memberId: Long): Flux<MemberDTO> {
        val sql = """
            SELECT
                f.to_id as member_id,
                m.username as username,
                m.password as password,
                m.email as email,
                m.nickname as nickname,
                m.student_number as student_number
            FROM member m
            JOIN follow f on m.id = f.from_id
            WHERE m.id =:memberId
        """.trimIndent()

        return r2dbcEntityTemplate.databaseClient
            .sql(sql)
            .bind("memberId", memberId)
            .map { row,metaData -> r2dbcConverter.read(MemberDTO::class.java, row) }
            .all()
    }

    override suspend fun findFollowings(memberId: Long): Flux<MemberDTO> {
        val sql = """
            SELECT
                f.from_id as member_id,
                m.username as username,
                m.password as password,
                m.email as email,
                m.nickname as nickname,
                m.student_number as student_number
            FROM member m
            JOIN follow f on m.id = f.to_id
            WHERE m.id =:memberId
        """.trimIndent()

        return r2dbcEntityTemplate.databaseClient
            .sql(sql)
            .bind("memberId", memberId)
            .map { row,metaData -> r2dbcConverter.read(MemberDTO::class.java, row) }
            .all()
    }
}