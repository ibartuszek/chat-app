package com.example.chatapp.service.user

import com.example.chatapp.dal.document.UserDocument
import com.example.chatapp.dal.repository.UserRepository
import com.example.chatapp.domain.NewUserRequest
import com.example.chatapp.domain.UserModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(val userTransformer: UserTransformer, val userRepository: UserRepository) {

    companion object {
        private val log = LoggerFactory.getLogger(UserService::class.java)
    }

    fun create(userRequest: NewUserRequest): UserModel {
        val savedUser = userRepository.save(userTransformer.createNewUserDocument(userRequest))
        log.info("New user saved into db id=${savedUser.id} name=${savedUser.name} email=${savedUser.email}")
        return userTransformer.transform(savedUser)
    }

    fun findById(id: String): UserModel = userTransformer.transform(getUserFromCollection(id))

    private fun getUserFromCollection(id: String) = userRepository.findById(id)
            .orElseThrow { throw createNoSuchElementException(id) }

    private fun createNoSuchElementException(id: String) = NoSuchElementException("User with id=${id} cannot be found")

    fun findAll() = userRepository.findAll()
            .map { userTransformer.transform(it) }
            .toCollection(arrayListOf())

    fun update(user: UserModel): UserModel {
        val savedUser = userRepository.save(updateDocument(user))
        log.info("User id=${savedUser.id} updated in db name=${savedUser.name} email=${savedUser.email}")
        return userTransformer.transform(savedUser)
    }

    private fun updateDocument(user: UserModel): UserDocument {
        val original = getUserFromCollection(user.id)
        original.name = user.name
        original.email = user.email
        original.active = true
        original.version += 1
        return original
    }

    fun delete(id: String) {
        if (!userRepository.existsById(id)) {
            throw createNoSuchElementException(id)
        }
        userRepository.deleteById(id)
    }

}