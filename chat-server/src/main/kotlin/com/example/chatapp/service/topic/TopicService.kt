package com.example.chatapp.service.topic

import com.example.chatapp.dal.repository.TopicRepository
import com.example.chatapp.domain.NewTopicRequest
import com.example.chatapp.domain.TopicModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TopicService(private val transformer: TopicTransformer, private val repository: TopicRepository) {

    companion object {
        private val log = LoggerFactory.getLogger(TopicService::class.java)
    }

    fun create(topicRequest: NewTopicRequest): TopicModel {
        val savedTopic = repository.save(transformer.transform(topicRequest))
        log.info("New topic created by user=${topicRequest.ownerId} name=${topicRequest.name}")
        return transformer.transform(savedTopic)
    }

    fun findByUserId(userId: String): List<TopicModel> {
        // should return by ownerId and userId
        val topicList = listOf(createMockTopic())
        log.info("Return topicList by userId=$userId size=${topicList.size}")
        return topicList
    }

    fun update(topic: TopicModel): TopicModel {
        // should send update message
        log.info("Topic id=${topic.id} is updated")
        return topic
    }

    fun close(id: String, ownerId: String) {
        // should send update message
        // should set it not active, reading can be allowed
        log.info("Topic id=$id is closed by userId=$ownerId")
    }

    // should delete after implementation
    private fun createMockTopic() = TopicModel.Builder()
            .id("fake topic id")
            .name("fake topic name")
            .ownerId("fake user id")
            .userIds(listOf("another fake user id"))
            .messages(emptyList())
            .active(true)
            .build()

}
