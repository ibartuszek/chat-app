package com.example.chatapp.utilities

import com.example.chatapp.dal.document.UserDocument
import com.example.chatapp.dal.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class MongoDbHelper(private val userRepository: UserRepository) {

    fun saveUser(document: UserDocument) {
        userRepository.save(document)
    }

    fun getUserById(id: String) = userRepository.findById(id)
            .orElseThrow { throw NoSuchElementException("User with id=$id cannot be found in test db") }!!

    fun getAllUser(): MutableList<UserDocument> = userRepository.findAll()

    fun deleteAllUser() {
        userRepository.deleteAll()
    }

    fun findUserByEmail(userEmail: String) = getAllUser().firstOrNull { it.email == userEmail }!!

}