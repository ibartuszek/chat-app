package com.example.chatapp.service.topic

import com.example.chatapp.domain.NewTopicRequest
import com.example.chatapp.domain.TopicModel
import com.example.chatapp.domain.UserModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TopicService {

    companion object {
        private val log = LoggerFactory.getLogger(TopicService::class.java)
    }

    fun create(topicRequest: NewTopicRequest): TopicModel {
        // implementation
        return createMockTopic()
    }

    fun findByUserId(userId: String): List<TopicModel> {
        // should return by ownerId and userId
        return listOf(createMockTopic())
    }

    fun update(topic: TopicModel): TopicModel {
        // should send update message
        return topic
    }

    fun disable(id: String) {
        // should send update message
        // should set it not active, reading can be allowed
    }

    // should delete after implementation
    private fun createMockTopic() = TopicModel.Builder()
            .id("fake topic id")
            .name("fake topic name")
            .ownerId("fake user id")
            .users(listOf(createMockUser()))
            .active(true)
            .build()

    // should delete after implementation
    private fun createMockUser() = UserModel.Builder()
            .id("another fake user id")
            .name("another fake user name")
            .email("another.fake.user@email.com")
            .active(true)
            .build()

}