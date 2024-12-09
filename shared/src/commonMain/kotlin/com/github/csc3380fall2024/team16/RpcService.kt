package com.github.csc3380fall2024.team16

import com.github.csc3380fall2024.team16.model.NewsArticle
import kotlinx.datetime.LocalDate
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc
import kotlinx.serialization.Serializable

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
    suspend fun login(usernameOrEmail: String, password: String): Session
    
    /**
     * @throws UnauthorizedException
     * @return id of the food log
     */
    suspend fun logFood(
        token: String,
        date: LocalDate,
        food: String,
        calories: Int,
        proteinGrams: Int,
        carbsGrams: Int,
        fatsGrams: Int,
    ): Int
    
    /**
     * @throws UnauthorizedException
     * @throws DoesNotExistException
     */
    suspend fun removeFoodLog(token: String, id: Int)
    
    /**
     * @throws UnauthorizedException
     */
    suspend fun getFoodLogs(token: String, date: LocalDate): List<FoodLog>
    
    /**
     * @throws UnauthorizedException
     */
    suspend fun getNewsArticles(token: String, query: String): List<NewsArticle>
}

class ValidationException(override val message: String) : Exception()
class UnauthorizedException : Exception()
class AlreadyExistsException(override val message: String) : Exception()
class DoesNotExistException(override val message: String) : Exception()

@Serializable
data class Session(val token: String, val username: String)

@Serializable
data class FoodLog(
    val id: Int,
    val food: String,
    val calories: Int,
    val proteinGrams: Int,
    val carbsGrams: Int,
    val fatsGrams: Int,
)
