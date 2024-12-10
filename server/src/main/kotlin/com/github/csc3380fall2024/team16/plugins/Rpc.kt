package com.github.csc3380fall2024.team16.plugins

import com.github.csc3380fall2024.team16.FoodLog
import com.github.csc3380fall2024.team16.Post
import com.github.csc3380fall2024.team16.RpcService
import com.github.csc3380fall2024.team16.Session
import com.github.csc3380fall2024.team16.TokenManager
import com.github.csc3380fall2024.team16.UnauthorizedException
import com.github.csc3380fall2024.team16.ValidationException
import com.github.csc3380fall2024.team16.emailRegex
import com.github.csc3380fall2024.team16.model.NewsArticle
import com.github.csc3380fall2024.team16.repository.NewsRepository
import com.github.csc3380fall2024.team16.repository.PostsRepository
import com.github.csc3380fall2024.team16.repository.UserRepository
import com.github.csc3380fall2024.team16.usernameRegex
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import kotlinx.datetime.LocalDate
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

fun Application.configureRpc(newsRepo: NewsRepository) {
    install(RPC)
    
    routing {
        rpc("/rpc") {
            rpcConfig {
                serialization {
                    json()
                }
            }
            
            registerService<RpcService> { ctx -> RpcServiceImpl(newsRepo, ctx) }
        }
    }
}

class RpcServiceImpl(
    private val newsRepo: NewsRepository,
    override val coroutineContext: CoroutineContext
) : RpcService {
    override suspend fun register(username: String, email: String, password: String) {
        when {
            username.length !in 3..24        -> throw ValidationException("username must be between 3 and 24 characters")
            !username.matches(usernameRegex) -> throw ValidationException("username must only contain letters, numbers, periods, and underscores")
            !email.matches(emailRegex)       -> throw ValidationException("invalid email")
            password.isEmpty()               -> throw ValidationException("password must not be empty")
        }
        
        UserRepository.addUser(username, email, password)
    }
    
    override suspend fun login(usernameOrEmail: String, password: String): Session {
        when {
            usernameOrEmail.isEmpty() -> throw ValidationException("username or email must not be empty")
            password.isEmpty()        -> throw ValidationException("password must not be empty")
        }
        
        val user = UserRepository.getUser(usernameOrEmail, password) ?: throw UnauthorizedException()
        return Session(
            token = TokenManager.createToken(user.id, user.passwordHash),
            username = user.username,
        )
    }
    
    override suspend fun logFood(
        token: String,
        date: LocalDate,
        food: String,
        calories: Int,
        proteinGrams: Int,
        carbsGrams: Int,
        fatsGrams: Int,
    ): Int {
        return transaction {
            val user = UserRepository.userFromToken(token) ?: throw UnauthorizedException()
            FoodLogs.insertAndGetId {
                it[FoodLogs.user] = user.id
                it[FoodLogs.date] = date
                it[FoodLogs.food] = food
                it[FoodLogs.calories] = calories
                it[FoodLogs.proteinGrams] = proteinGrams
                it[FoodLogs.carbsGrams] = carbsGrams
                it[FoodLogs.fatsGrams] = fatsGrams
            }.value
        }
    }
    
    override suspend fun removeFoodLog(token: String, id: Int) {
        transaction {
            val user = UserRepository.userFromToken(token) ?: throw UnauthorizedException()
            FoodLogs.deleteWhere { (FoodLogs.id eq id) and (FoodLogs.user eq user.id) }
        }
    }
    
    override suspend fun getFoodLogs(token: String, date: LocalDate): List<FoodLog> {
        val rows = transaction {
            val user = UserRepository.userFromToken(token) ?: throw UnauthorizedException()
            FoodLogs.select(
                FoodLogs.id,
                FoodLogs.food,
                FoodLogs.calories,
                FoodLogs.proteinGrams,
                FoodLogs.carbsGrams,
                FoodLogs.fatsGrams,
            ).where { (FoodLogs.user eq user.id) and (FoodLogs.date eq date) }.toList()
        }
        
        return rows.map {
            FoodLog(
                id = it[FoodLogs.id].value,
                food = it[FoodLogs.food],
                calories = it[FoodLogs.calories],
                proteinGrams = it[FoodLogs.proteinGrams],
                carbsGrams = it[FoodLogs.carbsGrams],
                fatsGrams = it[FoodLogs.fatsGrams],
            )
        }
    }
    
    override suspend fun getNewsArticles(token: String, query: String): List<NewsArticle> {
        UserRepository.userFromToken(token) ?: throw UnauthorizedException()
        return newsRepo.getNewsArticles(query)
    }
    
    override suspend fun getPosts(token: String): List<Post> {
        return transaction {
            UserRepository.userFromToken(token) ?: throw UnauthorizedException()
            PostsRepository.getPosts()
        }
    }
    
    override suspend fun addPost(token: String, description: String, image: ByteArray) {
        transaction {
            val user = UserRepository.userFromToken(token) ?: throw UnauthorizedException()
            PostsRepository.addPost(user.id, description, image)
        }
    }
}
