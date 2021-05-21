package com.example.chatapp.service.topic

import com.example.chatapp.dal.document.TopicDocument
import com.example.chatapp.domain.NewTopicRequest
import com.example.chatapp.domain.TopicModel
import org.springframework.stereotype.Component

@Component
class TopicTransformer {

    internal fun transform(request: NewTopicRequest) = TopicDocument.Builder()
            .ownerId(request.ownerId)
            .name(request.name)
            .active(true)
            .userIds(request.userIds)
            .messages(emptyList())
            .version(0L)
            .build()

    internal fun transform(document: TopicDocument) = TopicModel.Builder()
            .id(document.id!!)
            .ownerId(document.ownerId)
            .name(document.name)
            .active(document.active)
            .userIds(document.userIds)
            .messages(document.messages)
            .build()

}
