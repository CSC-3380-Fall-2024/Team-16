package com.github.csc3380fall2024.team16

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.github.csc3380fall2024.team16.plugins.Users
import com.github.csc3380fall2024.team16.server.BuildConfig
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.SecureRandom
import java.util.UUID
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

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

val usernameRegex = Regex("^[A-Za-z0-9._]+\$")
val emailRegex =
    Regex("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")

private val algorithm = Algorithm.HMAC256(BuildConfig.JWT_SECRET)

@OptIn(ExperimentalStdlibApi::class)
fun createToken(id: UUID, passwordHash: ByteArray): String {
    return JWT.create()
        .withSubject(id.toString())
        .withClaim("password_hash", passwordHash.toHexString()) // makes it so that password change invalidates JWT
        .sign(algorithm)
}

@OptIn(ExperimentalStdlibApi::class)
fun userFromToken(token: String): ResultRow? {
    val decodedJWT = try {
        JWT.require(algorithm).build().verify(token)
    } catch (exception: JWTVerificationException) {
        return null
    }
    
    val userID = try {
        UUID.fromString(decodedJWT.subject)
    } catch (e: IllegalArgumentException) {
        return null
    }
    
    val passwordHashStr = decodedJWT.getClaim("password_hash").asString() ?: return null
    
    val passwordHash = try {
        passwordHashStr.hexToByteArray()
    } catch (e: IllegalArgumentException) {
        return null
    }
    
    return transaction {
        Users.selectAll()
            .where((Users.id eq userID) and (Users.passwordHash eq passwordHash))
            .limit(1)
            .firstOrNull()
    }
}
