package com.example.chatapp.domain

import com.example.chatapp.domain.UserValidationMessages.Companion.EMPTY_ACTIVE
import com.example.chatapp.domain.UserValidationMessages.Companion.EMPTY_EMAIL
import com.example.chatapp.domain.UserValidationMessages.Companion.EMPTY_ID
import com.example.chatapp.domain.UserValidationMessages.Companion.EMPTY_NAME
import com.example.chatapp.domain.UserValidationMessages.Companion.NOT_VALID_EMAIL
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserValidationMessages private constructor() {
    companion object {
        internal const val EMPTY_ID = "User id cannot be empty"
        internal const val EMPTY_NAME = "User name cannot be empty"
        internal const val EMPTY_EMAIL = "User email cannot be empty"
        internal const val NOT_VALID_EMAIL = "User email must be valid"
        internal const val EMPTY_ACTIVE = "Active flag cannot be empty"
    }
}

data class UserModel constructor(
        @field:NotBlank(message = EMPTY_ID)
        val id: String,
        @field:NotBlank(message = EMPTY_NAME)
        val name: String,
        @field:NotBlank(message = EMPTY_EMAIL)
        @field:Email(message = NOT_VALID_EMAIL)
        val email: String,
        @field:NotNull(message = EMPTY_ACTIVE)
        val active: Boolean
) {

    data class Builder(
            var id: String? = null,
            var name: String? = null,
            var email: String? = null,
            var active: Boolean? = null
    ) {
        fun id(id: String) = apply { this.id = id }
        fun name(name: String) = apply { this.name = name }
        fun email(email: String) = apply { this.email = email }
        fun active(active: Boolean) = apply { this.active = active }
        fun build() = UserModel(id!!, name!!, email!!, active!!)
    }

}

data class NewUserRequest(
        @field:NotBlank(message = EMPTY_NAME)
        val name: String,
        @field:NotBlank(message = EMPTY_EMAIL)
        @field:Email(message = NOT_VALID_EMAIL)
        val email: String
)
