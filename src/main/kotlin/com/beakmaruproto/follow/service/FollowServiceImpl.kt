package com.beakmaruproto.follow.service

import com.beakmaruproto.follow.Follow
import com.beakmaruproto.follow.dto.FollowSaveDTO
import com.beakmaruproto.follow.dto.FollowDeleteDTO
import com.beakmaruproto.follow.repository.FollowRepository
import com.beakmaruproto.global.NotificationType
import com.beakmaruproto.global.SSESendProcessor
import com.beakmaruproto.member.dto.MemberDTO
import com.beakmaruproto.member.repository.MemberRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FollowServiceImpl @Autowired constructor(
    private val followRepository: FollowRepository,
    private val memberRepository: MemberRepository,
    private val sseSendProcessor: SSESendProcessor
) : FollowService {

    override suspend fun follow(followSaveDTO: FollowSaveDTO): Mono<MemberDTO> {
        return mono { followRepository.save(Follow(fromId = followSaveDTO.fromId, toId = followSaveDTO.toId)) }
            .flatMap { follow -> mono { memberRepository.findById(followSaveDTO.toId) } }
            .flatMap { member -> member.toDto() }
            .doOnNext { sseSendProcessor.personalSend(followSaveDTO.toId, NotificationType.to(NotificationType.FOLLOWER_ADD)) }
    }

    override suspend fun unfollow(followDeleteDTO: FollowDeleteDTO) {
//        return mono { followRepository.deleteAll() }
        TODO()
    }
}