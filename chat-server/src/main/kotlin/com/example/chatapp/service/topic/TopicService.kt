package com.example.chatapp.service.topic

import com.example.chatapp.dal.repository.TopicDao
import com.example.chatapp.domain.NewTopicRequest
import com.example.chatapp.domain.TopicModel
import com.example.chatapp.domain.TopicUpdateRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class TopicService(private val transformer: TopicTransformer, private val dao: TopicDao) {

    companion object {
        private val log = LoggerFactory.getLogger(TopicService::class.java)
    }

    fun create(topicRequest: NewTopicRequest): TopicModel {
        val savedTopic = dao.save(transformer.transform(topicRequest))
        log.info("New topic created by user=${topicRequest.ownerId} name=${topicRequest.name}")
        return transformer.transform(savedTopic)
    }

    fun findByUserId(userId: String): List<TopicModel> {
        val topicList = dao.findTopicsByUserId(userId)
        log.info("Return topicList by userId=$userId size=${topicList.size}")
        return topicList.map { transformer.transform(it) }
    }

    fun update(request: TopicUpdateRequest): TopicModel {
        if (request.active != null || request.name != null) {
            val document = dao.findById(request.id)
            log.info("Topic id=${topic.id} is updated")
            // should send update message
            return topic
        } else {
            throw IllegalStateException("NO UPDATES")
        }
    }

    fun close(id: String, ownerId: String) {
        // should send update message
        // should set it not active, reading can be allowed
        log.info("Topic id=$id is closed by userId=$ownerId")
    }

}
