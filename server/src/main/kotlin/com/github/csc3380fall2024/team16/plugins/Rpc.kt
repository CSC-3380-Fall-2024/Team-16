package com.github.csc3380fall2024.team16.plugins

import com.github.csc3380fall2024.team16.RpcService
import com.github.csc3380fall2024.team16.TokenManager
import com.github.csc3380fall2024.team16.UnauthorizedException
import com.github.csc3380fall2024.team16.ValidationException
import com.github.csc3380fall2024.team16.emailRegex
import com.github.csc3380fall2024.team16.model.NewsArticle
import com.github.csc3380fall2024.team16.news.NewsClient
import com.github.csc3380fall2024.team16.repository.UserRepository
import com.github.csc3380fall2024.team16.server.BuildConfig
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
    private val newsClient = NewsClient(BuildConfig.BING_SEARCH_API_KEY)
    
    override suspend fun register(username: String, email: String, password: String): String {
        when {
            username.length !in 3..24        -> throw ValidationException("username must be between 3 and 24 characters")
            !username.matches(usernameRegex) -> throw ValidationException("username must only contain letters, numbers, periods, and underscores")
            !email.matches(emailRegex)       -> throw ValidationException("invalid email")
            password.isEmpty()               -> throw ValidationException("password must not be empty")
        }
        
        val user = UserRepository.addUser(username, email, password)
        return TokenManager.createToken(user.id, user.passwordHash)
    }
    
    override suspend fun login(usernameOrEmail: String, password: String): String {
        when {
            usernameOrEmail.isEmpty() -> throw ValidationException("username or email must not be empty")
            password.isEmpty()        -> throw ValidationException("password must not be empty")
        }
        
        val user = UserRepository.getUser(usernameOrEmail, password) ?: throw UnauthorizedException()
        return TokenManager.createToken(user.id, user.passwordHash)
    }
    
    override suspend fun getNewsArticles(token: String, query: String): List<NewsArticle> {
        UserRepository.userFromToken(token) ?: throw UnauthorizedException()
        return newsClient.getNewsArticles(query)
    }
}
