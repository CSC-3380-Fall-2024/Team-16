package com.github.csc3380fall2024.team16

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDto(val username: String, val email: String, val password: String)

@Serializable
data class LoginDto(val usernameOrEmail: String, val password: String)
