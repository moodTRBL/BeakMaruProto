package com.beakmaruproto.follow.repository

import com.beakmaruproto.follow.dto.FollowDTO
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class FollowRepositoryCustomImpl @Autowired constructor(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val r2dbcConverter: MappingR2dbcConverter
) : FollowRepositoryCustom {
    override suspend fun findFollowingsById(memberId: Long): List<FollowDTO> {
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
            .collectList()
            .awaitSingle()
    }

    override suspend fun deleteFollowing(fromId: Long, toId: Long) {
        val sql = """
            DELETE
            FROM follow f
            WHERE f.from_id =:fromId and f.to_id =:toId
        """.trimIndent()
        r2dbcEntityTemplate.databaseClient
            .sql(sql)
            .bind("fromId", fromId)
            .bind("toId", toId)
            .await()
    }
}