package com.example.chatapp.domain

import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_ACTIVE
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_ID
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_MESSAGES
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_NAME
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_OWNER_ID
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_USER_IDS
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class TopicValidationMessages private constructor() {
    companion object {
        internal const val EMPTY_ID = "User id cannot be empty"
        internal const val EMPTY_OWNER_ID = "Owner id cannot be empty"
        internal const val EMPTY_NAME = "User name cannot be empty"
        internal const val EMPTY_ACTIVE = "Active flag cannot be empty"
        internal const val EMPTY_USER_IDS = "User ids cannot be empty"
        internal const val EMPTY_MESSAGES = "Messages cannot be empty"
    }
}

data class TopicModel constructor(
        @field:NotBlank(message = EMPTY_ID)
        val id: String,
        @field:NotBlank(message = EMPTY_OWNER_ID)
        val ownerId: String,
        @field:NotBlank(message = EMPTY_NAME)
        val name: String,
        @field:NotNull(message = EMPTY_ACTIVE)
        val active: Boolean,
        @field:NotEmpty(message = EMPTY_USER_IDS)
        val userIds: List<String>,
        @field:NotEmpty(message = EMPTY_MESSAGES)
        val messages: List<Message>
) {

    data class Builder(
            var id: String? = null,
            var ownerId: String? = null,
            var name: String? = null,
            var active: Boolean? = null,
            var userIds: List<String>? = null,
            var messages: List<Message>? = null
    ) {
        fun id(id: String) = apply { this.id = id }
        fun ownerId(ownerId: String) = apply { this.ownerId = ownerId }
        fun name(name: String) = apply { this.name = name }
        fun active(active: Boolean) = apply { this.active = active }
        fun userIds(userIds: List<String>) = apply { this.userIds = userIds }
        fun messages(messages: List<Message>) = apply { this.messages = messages }
        fun build() = TopicModel(id!!, ownerId!!, name!!, active!!, userIds!!, messages!!)
    }

}

data class NewTopicRequest(
        @field:NotBlank(message = EMPTY_NAME)
        val name: String,
        @field:NotBlank(message = EMPTY_OWNER_ID)
        val ownerId: String,
        @field:NotEmpty(message = EMPTY_USER_IDS)
        val userIds: List<String>
)

data class TopicUpdateRequest(
        var id: String,
        var ownerId: String,
        var name: String? = null,
        var active: Boolean? = null
)
