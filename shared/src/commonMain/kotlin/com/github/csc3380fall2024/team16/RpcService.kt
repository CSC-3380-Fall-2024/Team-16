package com.github.csc3380fall2024.team16

import com.github.csc3380fall2024.team16.model.NewsArticle
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc

@Rpc
interface RpcService : RemoteService {
    /**
     * @throws ValidationException
     * @throws AlreadyExistsException
     */
    suspend fun register(username: String, email: String, password: String)
    
    /**
     * @throws ValidationException
     * @throws UnauthorizedException
     */
    suspend fun login(usernameOrEmail: String, password: String): String
    
    /**
     * @throws UnauthorizedException
     */
    suspend fun getNewsArticles(token: String, query: String): List<NewsArticle>
}

class ValidationException(override val message: String) : Exception()
class UnauthorizedException : Exception()
class AlreadyExistsException(override val message: String) : Exception()
