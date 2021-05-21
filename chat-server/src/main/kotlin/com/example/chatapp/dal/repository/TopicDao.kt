package com.example.chatapp.dal.repository

import com.example.chatapp.dal.document.TopicDocument
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component
class TopicDao(private val topicRepository: TopicRepository, private val mongoTemplate: MongoTemplate) {

    // db.getCollection('topicCollection').find({
    // $or: [{ ownerId: "609e2376d4b6a54baa32c476"}, {userIds: {$in: ["609e2376d4b6a54baa32c476"]}}]
    // })
    fun findTopicsByUserId(userId: String): List<TopicDocument> {
        val criteria = Criteria().orOperator(
                Criteria("ownerId").`is`(userId),
                Criteria("userIds").`in`(listOf(userId)))
        return mongoTemplate.find(Query.query(criteria), TopicDocument::class.java)
    }

    fun save(document: TopicDocument) = topicRepository.save(document)

    /**
     * Troubleshooting, development method should be used only by Internal controller
     */
    fun saveAll(documentList: List<TopicDocument>): List<TopicDocument> = topicRepository.saveAll(documentList)

    /**
     * Troubleshooting, development method should be used only by Internal controller
     */
    fun findAll(): List<TopicDocument> = topicRepository.findAll()

    /**
     * Troubleshooting, development method should be used only by Internal controller
     */
    fun deleteAll() = topicRepository.deleteAll()

}

interface TopicRepository : MongoRepository<TopicDocument, String>
