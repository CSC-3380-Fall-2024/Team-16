package com.github.csc3380fall2024.team16

import com.github.csc3380fall2024.team16.model.NewsArticle
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc

@Rpc
interface RpcService : RemoteService {
    suspend fun register(username: String, email: String, password: String): String
    suspend fun login(usernameOrEmail: String, password: String): String
    
    suspend fun getNewsArticles(token: String, query: String): List<NewsArticle>
}
