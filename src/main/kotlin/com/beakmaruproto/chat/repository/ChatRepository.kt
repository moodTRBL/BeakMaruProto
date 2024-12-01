package com.beakmaruproto.chat.repository

import com.beakmaruproto.chat.Chat
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import reactor.core.publisher.Flux

interface ChatRepository : ReactiveMongoRepository<Chat, String> {

    @Tailable
    @Query("{senderId: ?0, receiverId: ?1}")
    fun findBySender(senderId: Long, receiverId: Long): Flux<Chat>

    @Tailable
    @Query("{receiverId: ?0}")
    fun listenSendMessage(receiverId: Long): Flux<Chat>

    @Tailable
    @Query("{roomNumber: ?0}")
    fun findByRoomNumber(roomNumber: Int): Flux<Chat>
}