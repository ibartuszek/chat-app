package com.example.chatapp.web.player

import com.example.chatapp.domain.NewUserRequest
import com.example.chatapp.domain.User
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/chat/rest/user")
class UserController {

    companion object {
        private val log = LoggerFactory.getLogger(UserController::class.java)
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody userRequest: NewUserRequest): User {
        log.info("New request to create user with name=${userRequest.name}")
        return User("fake", userRequest.name)
    }

    @GetMapping("/{id}")
    fun getById(@NotBlank @PathVariable id: String): User {
        log.info("Get user by id={}", id)
        return User(id, "John Doe")
    }

    @GetMapping("/all")
    fun getAll(): List<User> {
        log.info("Get all user")
        return Collections.emptyList()
    }

    @PatchMapping
    fun update(@Valid @RequestBody user: User): User {
        log.info("Update user={}", user)
        return user
    }

    @DeleteMapping("/{id}")
    fun deleteById(@NotBlank @PathVariable id: String) {
        log.info("Delete user by id={}", id)
    }

}
