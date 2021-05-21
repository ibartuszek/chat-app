package com.example.chatapp.dal.document

import com.example.chatapp.domain.Message
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "topicCollection")
class TopicDocument (
        @Id
        var id: String?,
        var ownerId: String,
        var name: String,
        var active: Boolean,
        var userIds: List<String>,
        var messages: List<Message>,
        @Version
        var version: Long
) {

    data class Builder(
            var id: String? = null,
            var ownerId: String? = null,
            var name: String? = null,
            var active: Boolean? = null,
            var userIds: List<String>? = null,
            var messages: List<Message>? = null,
            var version: Long? = null
    ) {
        fun id(id: String) = apply { this.id = id }
        fun ownerId(ownerId: String) = apply { this.ownerId = ownerId }
        fun name(name: String) = apply { this.name = name }
        fun active(active: Boolean) = apply { this.active = active }
        fun userIds(userIds: List<String>) = apply { this.userIds = userIds }
        fun messages(messages: List<Message>) = apply { this.messages = messages }
        fun version(version: Long) = apply { this.version = version }
        fun build() = TopicDocument(id, ownerId!!, name!!, active!!, userIds!!, messages!!, version!!)
    }

}
