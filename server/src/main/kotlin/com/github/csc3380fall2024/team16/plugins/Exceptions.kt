package com.github.csc3380fall2024.team16.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

class ConflictException(message: String) : Exception(message)
class InvalidCredentialsException : Exception("invalid credentials")

fun Application.configureExceptions() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }
        
        exception<ConflictException> { call, cause ->
            call.respond(HttpStatusCode.Conflict, cause.message!!)
        }
        
        exception<InvalidCredentialsException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, cause.message!!)
        }
    }
}
