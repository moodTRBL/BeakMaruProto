package com.beakmaruproto.follow.repository

import com.beakmaruproto.follow.dto.FollowDTO
import com.beakmaruproto.member.dto.MemberDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class FollowCustomRepositoryImpl @Autowired constructor(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val r2dbcConverter: MappingR2dbcConverter
) : FollowCustomRepository {
    override suspend fun findFollowingsId(memberId: Long): Flux<FollowDTO> {
        val sql = """
            SELECT
                f.id as follow_id,
                f.to_id as to_id,
                f.from_id as from_id
            FROM member m
            JOIN follow f on m.id = f.from_id
            WHERE m.id =:memberId
        """.trimIndent()

        return r2dbcEntityTemplate.databaseClient
            .sql(sql)
            .bind("memberId", memberId)
            .map { row,metaData -> r2dbcConverter.read(FollowDTO::class.java, row) }
            .all()
    }
}