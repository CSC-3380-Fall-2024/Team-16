package com.github.csc3380fall2024.team16.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.csc3380fall2024.team16.LoginDto
import com.github.csc3380fall2024.team16.RegisterDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.SecureRandom
import java.util.Date
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@OptIn(ExperimentalStdlibApi::class)
fun Application.configureRouting() {
    routing {
        post("/register") {
            val body = call.receive<RegisterDto>()
            val salt = createRandomSalt()
            val hash = createHash(body.password, salt)
            transaction {
                val usernameExists = run {
                    val existsOp = exists(Users.selectAll().where(Users.username eq body.username))
                    Table.Dual.select(existsOp).first()[existsOp]
                }
                if (usernameExists) throw ConflictException("user with that username already exists")
                
                val emailExists = run {
                    val existsOp = exists(Users.selectAll().where(Users.email eq body.email))
                    Table.Dual.select(existsOp).first()[existsOp]
                }
                if (emailExists) throw ConflictException("user with that email already exists")
                
                Users.insert {
                    it[username] = body.username
                    it[email] = body.email
                    it[passwordSalt] = salt
                    it[passwordHash] = hash
                }
            }
            call.response.status(HttpStatusCode.Created)
        }
        post("/login") {
            val body = call.receive<LoginDto>()
            val row = transaction {
                Users.selectAll()
                    .where { (Users.username eq body.usernameOrEmail) or (Users.email eq body.usernameOrEmail) }
                    .limit(1)
                    .firstOrNull() ?: throw InvalidCredentialsException()
            }
            
            val username = row[Users.username]
            val email = row[Users.email]
            val passwordSalt = row[Users.passwordSalt]
            val passwordHash = row[Users.passwordHash]
            
            if (!createHash(body.password, passwordSalt).contentEquals(passwordHash)) {
                throw InvalidCredentialsException()
            }
            
            val token = JWT.create()
                .withAudience("temp")
                .withIssuer("temp")
                .withClaim("username", username)
                .withClaim("email", email)
                .withClaim("passwordHash", passwordHash.toHexString())
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256("temp"))
            
            call.respond(hashMapOf("token" to token))
        }
    }
}

// returns 16 byte salt
fun createRandomSalt(): ByteArray {
    val random = SecureRandom()
    val salt = ByteArray(16)
    random.nextBytes(salt)
    return salt
}

// returns 128 byte hash
fun createHash(password: String, salt: ByteArray): ByteArray {
    val spec = PBEKeySpec(password.toCharArray(), salt, 65536, 128)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    return factory.generateSecret(spec).encoded
}
