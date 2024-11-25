package com.beakmaruproto.member.controller

import com.beakmaruproto.logger
import com.beakmaruproto.member.dto.MemberDTO
import com.beakmaruproto.member.dto.MemberLoginRequest
import com.beakmaruproto.member.dto.MemberRequest
import com.beakmaruproto.member.dto.MemberResponse
import com.beakmaruproto.member.repository.MemberRepository
import com.beakmaruproto.member.service.MemberService
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServerHttpRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.result.view.RequestContext
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Controller
class MemberController @Autowired constructor(
    private val memberService: MemberService,
) {
    val log = logger()

    @PostMapping("/member/sign-up")
    suspend fun signUp(@RequestBody memberRequest: MemberRequest): Mono<ResponseEntity<MemberResponse>> {
        return memberService.singUp(memberRequest.toSaveDTO())
            .flatMap { dto -> mono { ResponseEntity.ok(dto.toSaveResponse()) } }
    }

    @PostMapping("/member/login", consumes = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun login(
       @RequestBody memberLoginRequest: MemberLoginRequest,
       session: WebSession
    ): Mono<ResponseEntity<MemberDTO>> {
        return Mono.defer { memberService.getMember(memberLoginRequest.username) }
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap { if (it == null) Mono.empty() else Mono.just(it) }
            .filter { member -> memberLoginRequest.password == member.password }
            .doOnNext { member ->  session.attributes["member"] = member }
            .map { dto ->
                ResponseEntity
                    .ok()
                    .header("X-AUTH-TOKEN", session.id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto)
            }
    }

    @PutMapping("/member/logout")
    fun logout(session: WebSession): Mono<Void> {
        return Mono.just(session)
            .flatMap { session.invalidate() }
    }

    @PutMapping("member/update")
    suspend fun updateMember(@RequestBody memberRequest: MemberRequest): Mono<ResponseEntity<MemberResponse>> {
        return memberService.updateMember(memberRequest.toUpdateDTO())
        .flatMap { dto -> mono { ResponseEntity.ok(dto.toSaveResponse()) } }
    }
}