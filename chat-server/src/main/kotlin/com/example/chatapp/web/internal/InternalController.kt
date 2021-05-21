package com.example.chatapp.web.internal

import com.example.chatapp.dal.document.TopicDocument
import com.example.chatapp.dal.document.UserDocument
import com.example.chatapp.dal.repository.TopicDao
import com.example.chatapp.dal.repository.TopicRepository
import com.example.chatapp.dal.repository.UserRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/chat/internal")
class InternalController(private val userRepository: UserRepository, private val topicDao: TopicDao) {

    @PostMapping("/user/create")
    fun createUsers(@Valid @RequestBody userDocumentList: List<UserDocument>) = userRepository.saveAll(userDocumentList)
            .size

    @GetMapping("/user/all")
    fun findAllUser(): List<UserDocument> = userRepository.findAll()

    @DeleteMapping("/user/clean")
    fun deleteAllUser(): Long {
        val size = userRepository.count()
        userRepository.deleteAll()
        return size
    }

    @PostMapping("/topic/create")
    fun createTopics(@Valid @RequestBody topicDocumentList: List<TopicDocument>) =
            topicDao.saveAll(topicDocumentList).size

    @GetMapping("/topic/all")
    fun findAllTopic(): List<TopicDocument> = topicDao.findAll()

    @DeleteMapping("/topic/clean")
    fun deleteAllTopic() = topicDao.deleteAll()


}
