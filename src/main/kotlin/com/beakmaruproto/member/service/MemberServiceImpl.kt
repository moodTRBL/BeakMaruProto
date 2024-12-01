package com.beakmaruproto.member.service

import com.beakmaruproto.global.NotificationType
import com.beakmaruproto.global.service.SSESendService
import com.beakmaruproto.member.dto.MemberDTO
import com.beakmaruproto.member.dto.MemberLoginDTO
import com.beakmaruproto.member.dto.MemberSaveDTO
import com.beakmaruproto.member.dto.MemberUpdateDTO
import com.beakmaruproto.member.repository.MemberRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class MemberServiceImpl @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val sseSendService: SSESendService,
    private val passwordEncoder: PasswordEncoder
) : MemberService {
    override suspend fun signUp(memberSaveDTO: MemberSaveDTO): Mono<MemberDTO> {
        return validateMember(memberSaveDTO.username)
            .then(mono { memberRepository.save(memberSaveDTO.toEntity()) })
            .flatMap { it -> it.toDto() }
            .doOnNext{ sseSendService.personalSend(it.memberId, NotificationType.to(NotificationType.MEMBER_SAVE)) }
    }

    override suspend fun signIn(memberLoginDTO: MemberLoginDTO): Mono<MemberDTO> {
        return Mono.defer { getMember(memberLoginDTO.username) }
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap { if (it == null) Mono.empty() else Mono.just(it) }
            .filter { member -> memberLoginDTO.password == member.password }
            .switchIfEmpty { Mono.error(Exception("인증 실패")) }
    }

    override suspend fun updateMember(memberUpdateDTO: MemberUpdateDTO): Mono<MemberDTO> {
        return memberRepository.findByUsername(memberUpdateDTO.originUsername)
            .flatMap { findMember -> memberUpdateDTO.toUpdateEntity(findMember) }
            .flatMap { updateMember -> mono { memberRepository.save(updateMember) } }
            .flatMap { it -> it.toDto() }
            .doOnNext{ sseSendService.personalSend(it.memberId, NotificationType.to(NotificationType.MEMBER_UPDATE)) }
    }

    override fun getMember(username: String): Mono<MemberDTO> {
        return memberRepository.findByUsername(username)
            .flatMap { member -> member.toDto() }
    }

    private suspend fun validateMember(username: String): Mono<Void> {
        return memberRepository.existsByUsername(username)
            .flatMap { it ->
                if (it) {
                    Mono.error(Exception("overlap member username: $username"))
                } else {
                    Mono.empty()
                }
            }
    }
}