package com.github.csc3380fall2024.team16.repository

import com.github.csc3380fall2024.team16.AlreadyExistsException
import com.github.csc3380fall2024.team16.TokenData
import com.github.csc3380fall2024.team16.TokenManager
import com.github.csc3380fall2024.team16.plugins.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.SecureRandom
import java.util.UUID
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object UserRepository {
    /**
     * @throws AlreadyExistsException
     */
    fun addUser(username: String, email: String, password: String) {
        val salt = createRandomSalt()
        val hash = createHash(password, salt)
        transaction {
            val usernameExists = run {
                val existsOp = exists(Users.selectAll().where(Users.username.lowerCase() eq username.lowercase()))
                Table.Dual.select(existsOp).first()[existsOp]
            }
            if (usernameExists) throw AlreadyExistsException("user with that username already exists")
            
            val emailExists = run {
                val existsOp = exists(Users.selectAll().where(Users.email.lowerCase() eq email.lowercase()))
                Table.Dual.select(existsOp).first()[existsOp]
            }
            if (emailExists) throw AlreadyExistsException("user with that email already exists")
            
            Users.insert {
                it[Users.username] = username
                it[Users.email] = email
                it[passwordSalt] = salt
                it[passwordHash] = hash
            }
        }
    }
    
    fun getUser(usernameOrEmail: String, password: String): User? {
        val row = transaction {
            Users.selectAll()
                .where {
                    (Users.username.lowerCase() eq usernameOrEmail.lowercase()) or (Users.email.lowerCase() eq usernameOrEmail.lowercase())
                }
                .limit(1)
                .firstOrNull()
        } ?: return null
        
        val user = User(
            id = row[Users.id].value,
            username = row[Users.username],
            email = row[Users.email],
            passwordSalt = row[Users.passwordSalt],
            passwordHash = row[Users.passwordHash]
        )
        
        if (!createHash(password, user.passwordSalt).contentEquals(user.passwordHash)) {
            return null
        }
        
        return user
    }
    
    fun userFromToken(token: String): User? {
        val tokenData = TokenManager.decodeToken(token) ?: return null
        return userFromToken(tokenData)
    }
    
    private fun userFromToken(token: TokenData): User? {
        val row = transaction {
            Users.selectAll()
                .where((Users.id eq token.userID) and (Users.passwordHash eq token.passwordHash))
                .limit(1)
                .firstOrNull()
        } ?: return null
        
        return User(
            id = row[Users.id].value,
            username = row[Users.username],
            email = row[Users.email],
            passwordSalt = row[Users.passwordSalt],
            passwordHash = row[Users.passwordHash]
        )
    }
    
    // returns 16 byte salt
    private fun createRandomSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }
    
    // returns 128 byte hash
    private fun createHash(password: String, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        return factory.generateSecret(spec).encoded
    }
    
    fun getPfp(username: String): ByteArray? {
        val row = Users.select(Users.pfp)
            .where(Users.username.lowerCase() eq username.lowercase())
            .singleOrNull() ?: return null
        
        return row[Users.pfp]
    }
}

class User(
    val id: UUID,
    val username: String,
    val email: String,
    val passwordSalt: ByteArray,
    val passwordHash: ByteArray,
)
