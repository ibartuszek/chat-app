package com.example.chatapp.web.topic

import com.example.chatapp.domain.NewTopicRequest
import com.example.chatapp.domain.TopicModel
import com.example.chatapp.domain.TopicUpdateRequest
import com.example.chatapp.service.topic.TopicService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/chat/rest/topic")
class TopicController(private val topicService: TopicService) {

    companion object {
        private val log = LoggerFactory.getLogger(TopicController::class.java)
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody topicRequest: NewTopicRequest): TopicModel {
        log.info("New request to create topic with name=${topicRequest.name} by user=${topicRequest.ownerId}")
        return topicService.create(topicRequest)
    }

    @GetMapping("/{userId}")
    fun getByUserId(@NotBlank @PathVariable userId: String): List<TopicModel> {
        log.info("Get user topics by id={}", userId)
        return topicService.findByUserId(userId)
    }

    @PatchMapping
    fun update(@Valid @RequestBody request: TopicUpdateRequest): TopicModel {
        log.info("Update topic id=${request.id} by owner=${request.ownerId} "
                + "name=${request.ownerId} active=${request.ownerId}")
        return topicService.update(request)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@NotBlank @PathVariable id: String, @NotBlank @RequestBody ownerId: String) {
        log.info("Delete topic by id={}", id)
        topicService.close(id, ownerId)
    }

}
