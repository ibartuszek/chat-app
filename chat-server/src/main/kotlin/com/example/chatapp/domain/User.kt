package com.example.chatapp.domain

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserModel constructor(
        @NotBlank
        val id: String,
        @NotBlank
        val name: String,
        @Email
        val email: String,
        @NotNull
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
        @NotBlank
        val name: String,
        @Email
        val email: String
)
