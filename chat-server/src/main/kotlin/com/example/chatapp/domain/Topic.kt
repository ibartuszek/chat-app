package com.example.chatapp.domain

import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_ACTIVE
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_ID
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_NAME
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_OWNER_ID
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_USERS
import com.example.chatapp.domain.TopicValidationMessages.Companion.EMPTY_USER_IDS
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class TopicValidationMessages private constructor() {
    companion object {
        internal const val EMPTY_ID = "User id cannot be empty"
        internal const val EMPTY_NAME = "User name cannot be empty"
        internal const val EMPTY_OWNER_ID = "Owner id cannot be empty"
        internal const val EMPTY_USERS = "Users cannot be empty"
        internal const val EMPTY_USER_IDS = "User ids cannot be empty"
        internal const val EMPTY_ACTIVE = "Active flag cannot be empty"
    }
}

data class TopicModel constructor(
        @field:NotBlank(message = EMPTY_ID)
        val id: String,
        @field:NotBlank(message = EMPTY_NAME)
        val name: String,
        @field:NotBlank(message = EMPTY_OWNER_ID)
        val ownerId: String,
        @field:NotEmpty(message = EMPTY_USERS)
        val users: List<UserModel>,
        @field:NotNull(message = EMPTY_ACTIVE)
        val active: Boolean
) {

    data class Builder(
            var id: String? = null,
            var name: String? = null,
            var ownerId: String? = null,
            var users: List<UserModel>? = null,
            var active: Boolean? = null
    ) {
        fun id(id: String) = apply { this.id = id }
        fun name(name: String) = apply { this.name = name }
        fun ownerId(ownerId: String) = apply { this.ownerId = ownerId }
        fun users(users: List<UserModel>) = apply { this.users = users }
        fun active(active: Boolean) = apply { this.active = active }
        fun build() = TopicModel(id!!, name!!, ownerId!!, users!!, active!!)
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
