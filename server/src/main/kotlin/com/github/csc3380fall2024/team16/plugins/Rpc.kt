package com.github.csc3380fall2024.team16.plugins

import com.github.csc3380fall2024.team16.RpcService
import com.github.csc3380fall2024.team16.TokenManager
import com.github.csc3380fall2024.team16.emailRegex
import com.github.csc3380fall2024.team16.repository.UserRepository
import com.github.csc3380fall2024.team16.usernameRegex
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
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
        
        val user = UserRepository.addUser(username, email, password)
        return TokenManager.createToken(user.id, user.passwordHash)
    }
    
    override suspend fun login(usernameOrEmail: String, password: String): String {
        when {
            usernameOrEmail.isEmpty() -> throw Exception("username or email must not be empty")
            password.isEmpty()        -> throw Exception("password must not be empty")
        }
        
        val user = UserRepository.getUser(usernameOrEmail, password) ?: throw Exception("invalid credentials")
        return TokenManager.createToken(user.id, user.passwordHash)
    }
}
