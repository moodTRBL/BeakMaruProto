package com.beakmaruproto.member.controller

import com.beakmaruproto.logger
import com.beakmaruproto.member.dto.MemberRequest
import com.beakmaruproto.member.dto.MemberResponse
import com.beakmaruproto.member.service.MemberService
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono

@Controller
class MemberController @Autowired constructor(
    private val memberService: MemberService
) {
    val log = logger()

    @PostMapping("/member/sign-up")
    suspend fun signUp(@RequestBody memberRequest: MemberRequest): Mono<ResponseEntity<MemberResponse>> {
        return memberService.singUp(memberRequest.toSaveDTO())
            .flatMap { dto -> mono { ResponseEntity.ok(dto.toSaveResponse()) } }
    }

    @PutMapping("member/update")
    suspend fun updateMember(@RequestBody memberRequest: MemberRequest): Mono<ResponseEntity<MemberResponse>> {
        return memberService.updateMember(memberRequest.toUpdateDTO())
        .flatMap { dto -> mono { ResponseEntity.ok(dto.toSaveResponse()) } }
    }
}