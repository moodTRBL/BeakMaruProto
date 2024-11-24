package com.beakmaruproto.member.service

import com.beakmaruproto.member.dto.MemberDTO
import com.beakmaruproto.member.dto.MemberSaveDTO
import com.beakmaruproto.member.dto.MemberUpdateDTO
import reactor.core.publisher.Mono

interface MemberService {
    suspend fun singUp(memberSaveDTO: MemberSaveDTO): Mono<MemberDTO>
    suspend fun updateMember(memberUpdateDTO: MemberUpdateDTO): Mono<MemberDTO>
}