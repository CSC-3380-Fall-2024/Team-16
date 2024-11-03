package com.github.csc3380fall2024.team16.plugins

import com.github.csc3380fall2024.team16.LoginDto
import com.github.csc3380fall2024.team16.RegisterDto
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun Application.configureValidation() {
    val usernameRegex = Regex("^[A-Za-z0-9._]+\$")
    val emailRegex =
        Regex("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
    
    install(RequestValidation) {
        validate<RegisterDto> {
            when {
                it.username.length !in 3..24        -> ValidationResult.Invalid("username must be between 3 and 24 characters")
                !it.username.matches(usernameRegex) -> ValidationResult.Invalid("username must only contain letters, numbers, periods, and underscores")
                !it.email.matches(emailRegex)       -> ValidationResult.Invalid("invalid email")
                it.password.isEmpty()               -> ValidationResult.Invalid("password must not be empty")
                else                                -> ValidationResult.Valid
            }
        }
        
        validate<LoginDto> {
            when {
                it.usernameOrEmail.isEmpty() -> ValidationResult.Invalid("username or email must not be empty")
                it.password.isEmpty()        -> ValidationResult.Invalid("password must not be empty")
                else                         -> ValidationResult.Valid
            }
        }
    }
}
