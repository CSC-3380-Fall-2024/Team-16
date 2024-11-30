package com.github.csc3380fall2024.team16

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.github.csc3380fall2024.team16.server.BuildConfig
import java.util.UUID

object TokenManager {
    private val algorithm = Algorithm.HMAC256(BuildConfig.JWT_SECRET)
    private val verifier = JWT.require(algorithm).build()
    
    @OptIn(ExperimentalStdlibApi::class)
    fun createToken(id: UUID, passwordHash: ByteArray): String {
        return JWT.create()
            .withSubject(id.toString())
            .withClaim("password_hash", passwordHash.toHexString()) // makes it so that password change invalidates JWT
            .sign(algorithm)
    }
    
    @OptIn(ExperimentalStdlibApi::class)
    fun decodeToken(token: String): TokenData? {
        val decodedJWT = try {
            verifier.verify(token)
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
        
        return TokenData(userID, passwordHash)
    }
}

class TokenData(val userID: UUID, val passwordHash: ByteArray)
