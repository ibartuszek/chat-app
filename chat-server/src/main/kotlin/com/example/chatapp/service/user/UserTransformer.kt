package com.example.chatapp.service.user

import com.example.chatapp.dal.document.UserDocument
import com.example.chatapp.domain.NewUserRequest
import com.example.chatapp.domain.UserModel
import org.springframework.stereotype.Component

@Component
class UserTransformer {

    internal fun createNewUserDocument(userRequest: NewUserRequest) = UserDocument.Builder()
            .name(userRequest.name)
            .email(userRequest.email)
            .active(true)
            .version(0L)
            .build()

    internal fun transform(document: UserDocument) = UserModel.Builder()
            .id(document.id!!)
            .name(document.name)
            .email(document.email)
            .active(document.active)
            .build()

}
