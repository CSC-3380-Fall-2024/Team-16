package com.github.csc3380fall2024.team16.plugins

import com.github.csc3380fall2024.team16.RpcService
import com.github.csc3380fall2024.team16.createHash
import com.github.csc3380fall2024.team16.createRandomSalt
import com.github.csc3380fall2024.team16.createToken
import com.github.csc3380fall2024.team16.emailRegex
import com.github.csc3380fall2024.team16.userFromToken
import com.github.csc3380fall2024.team16.usernameRegex
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

fun Application.configureRpc() {
    install(RPC)
    
    routing {
        rpc("/rpc") {
            rpcConfig {
                serialization {
                    json()
                }
            }
            
            registerService<RpcService> { ctx -> RpcServiceImpl(ctx) }
        }
    }
}

class RpcServiceImpl(override val coroutineContext: CoroutineContext) : RpcService {
    override suspend fun register(username: String, email: String, password: String): String {
        when {
            username.length !in 3..24        -> throw Exception("username must be between 3 and 24 characters")
            !username.matches(usernameRegex) -> throw Exception("username must only contain letters, numbers, periods, and underscores")
            !email.matches(emailRegex)       -> throw Exception("invalid email")
            password.isEmpty()               -> throw Exception("password must not be empty")
        }
        
        val salt = createRandomSalt()
        val hash = createHash(password, salt)
        val id = transaction {
            val usernameExists = run {
                val existsOp = exists(Users.selectAll().where(Users.username eq username))
                Table.Dual.select(existsOp).first()[existsOp]
            }
            if (usernameExists) throw Exception("user with that username already exists")
            
            val emailExists = run {
                val existsOp = exists(Users.selectAll().where(Users.email eq email))
                Table.Dual.select(existsOp).first()[existsOp]
            }
            if (emailExists) throw Exception("user with that email already exists")
            
            Users.insertAndGetId {
                it[Users.username] = username
                it[Users.email] = email
                it[passwordSalt] = salt
                it[passwordHash] = hash
            }
        }
        
        return createToken(id.value, hash)
    }
    
    override suspend fun login(usernameOrEmail: String, password: String): String {
        when {
            usernameOrEmail.isEmpty() -> throw Exception("username or email must not be empty")
            password.isEmpty()        -> throw Exception("password must not be empty")
        }
        
        val row = transaction {
            Users.selectAll()
                .where { (Users.username eq usernameOrEmail) or (Users.email eq usernameOrEmail) }
                .limit(1)
                .firstOrNull() ?: throw Exception("invalid credentials")
        }
        
        if (!createHash(password, row[Users.passwordSalt]).contentEquals(row[Users.passwordHash])) {
            throw Exception("invalid credentials")
        }
        
        return createToken(row[Users.id].value, row[Users.passwordHash])
    }
}
