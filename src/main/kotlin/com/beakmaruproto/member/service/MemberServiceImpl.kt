package com.beakmaruproto.member.service

import com.beakmaruproto.member.dto.MemberDTO
import com.beakmaruproto.member.dto.MemberSaveDTO
import com.beakmaruproto.member.dto.MemberUpdateDTO
import com.beakmaruproto.member.repository.MemberRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MemberServiceImpl @Autowired constructor(
    private val memberRepository: MemberRepository
) : MemberService {
    override suspend fun singUp(memberSaveDTO: MemberSaveDTO): Mono<MemberDTO> {
        return validateMember(memberSaveDTO.username)
            .then(mono { memberRepository.save(memberSaveDTO.toEntity()) })
            .flatMap { it -> it.toDto() }
    }

    override suspend fun updateMember(memberUpdateDTO: MemberUpdateDTO): Mono<MemberDTO> {
        return memberRepository.findByUsername(memberUpdateDTO.originUsername)
            .flatMap { findMember -> memberUpdateDTO.toUpdateEntity(findMember) }
            .flatMap { updateMember -> mono { memberRepository.save(updateMember) } }
            .flatMap { it -> it.toDto() }
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