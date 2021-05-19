package com.example.chatapp.web.user

import com.example.chatapp.domain.NewUserRequest
import com.example.chatapp.domain.UserModel
import com.example.chatapp.service.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/chat/rest/user")
class UserController(private val userService: UserService) {

    companion object {
        private val log = LoggerFactory.getLogger(UserController::class.java)
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody userRequest: NewUserRequest): UserModel {
        log.info("New request to create user with name=${userRequest.name}")
        return userService.create(userRequest)
    }

    @GetMapping("/{id}")
    fun getById(@NotBlank @PathVariable id: String): UserModel {
        log.info("Get user by id={}", id)
        return userService.findById(id)
    }

    @GetMapping("/all")
    fun getAll(): List<UserModel> {
        log.info("Get all user")
        return userService.findAll()
    }

    @PatchMapping
    fun update(@Valid @RequestBody user: UserModel): UserModel {
        log.info("Update user={}", user)
        return userService.update(user)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@NotBlank @PathVariable id: String) {
        log.info("Delete user by id={}", id)
        userService.anonymize(id)
    }

}
