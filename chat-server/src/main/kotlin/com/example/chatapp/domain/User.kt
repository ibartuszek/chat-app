package com.example.chatapp.domain

import kotlinx.serialization.Serializable
import javax.validation.constraints.NotBlank

@Serializable
data class User(@NotBlank val id: String, @NotBlank val name: String)

@Serializable
data class NewUserRequest(@NotBlank val name: String)
