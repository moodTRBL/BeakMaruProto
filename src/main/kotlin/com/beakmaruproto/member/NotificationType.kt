package com.beakmaruproto.member

enum class NotificationType(
    private val messageDTO: MessageDTO
) {
    MEMBER_UPDATE(MessageDTO("member_update", "사용자 정보 수정 완료")),
    MEMBER_SAVE(MessageDTO("member_save", "회원가입을 환영합니다!")),
    ;

    companion object {
        fun to(notificationType: NotificationType) = notificationType.messageDTO
    }
}