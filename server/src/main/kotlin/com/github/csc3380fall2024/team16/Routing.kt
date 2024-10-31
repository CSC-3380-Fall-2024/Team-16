package com.github.csc3380fall2024.team16

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Date

fun Application.configureRouting() {
    routing {
        post("/register") {
            val body = call.receive<RegisterBody>()
            transaction {
                Users.insert {
                    it[username] = body.username
                    it[email] = body.email
                    it[passwordHash] = body.password
                }
            }
            call.response.status(HttpStatusCode.Created)
        }
        post("login") {
            val body = call.receive<LoginBody>()
            val row = transaction {
                Users.selectAll()
                    .where { ((Users.username eq body.usernameOrEmail) or (Users.email eq body.usernameOrEmail)) and (Users.passwordHash eq body.password) }
                    .limit(1)
                    .firstOrNull()
            }
            
            if (row == null) {
                return@post call.respondText(
                    "Could not find user with those credentials",
                    status = HttpStatusCode.BadRequest
                )
            }
            
            val username = row[Users.username]
            val email = row[Users.email]
            val passwordHash = row[Users.passwordHash]
            
            val token = JWT.create()
                .withAudience("temp")
                .withIssuer("temp")
                .withClaim("username", username)
                .withClaim("email", email)
                .withClaim("passwordHash", passwordHash)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256("temp"))
            
            call.respond(hashMapOf("token" to token))
        }
    }
}

@Serializable
data class RegisterBody(val username: String, val email: String, val password: String)

@Serializable
data class LoginBody(val usernameOrEmail: String, val password: String)