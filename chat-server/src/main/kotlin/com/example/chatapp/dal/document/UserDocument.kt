package com.example.chatapp.dal.document

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "userCollection")
data class UserDocument(
        @Id
        var id: String?,
        var name: String,
        var email: String,
        var active: Boolean,
        @Version
        var version: Long) {

    data class Builder(
            var id: String? = null,
            var name: String? = null,
            var email: String? = null,
            var active: Boolean? = null,
            var version: Long? = null
    ) {
        fun id(id: String) = apply { this.id = id }
        fun name(name: String) = apply { this.name = name }
        fun email(email: String) = apply { this.email = email }
        fun active(active: Boolean) = apply { this.active = active }
        fun version(version: Long) = apply { this.version = version }
        fun build() = UserDocument(id, name!!, email!!, active!!, version!!)
    }
}
