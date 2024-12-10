package com.github.csc3380fall2024.team16.repository

import com.github.csc3380fall2024.team16.FoodLog
import com.github.csc3380fall2024.team16.RpcClient
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.io.files.Path
import kotlinx.serialization.Serializable

class FoodLogsRepository(private val client: RpcClient, path: Path) {
    private val store: KStore<FoodLogs> = storeOf(file = path, default = FoodLogs())
    
    val updates = store.updates.stateIn(CoroutineScope(Job()), SharingStarted.Eagerly, runBlocking { store.get() })
    
    suspend fun fetch(token: String, vararg dates: LocalDate) = coroutineScope {
        val tasks = dates.map { date ->
            async {
                date to client.rpc { getFoodLogs(token, date) }
            }
        }
        
        val logs = tasks.awaitAll().toMap()
        
        store.update {
            it?.copy(logs = logs)
        }
    }
    
    suspend fun setGoals(calorieGoal: Int, proteinGoal: Int, fatGoal: Int, carbsGoal: Int) {
        store.update {
            it?.copy(
                calorieGoal = calorieGoal,
                proteinGoal = proteinGoal,
                fatGoal = fatGoal,
                carbsGoal = carbsGoal,
            )
        }
    }
    
    suspend fun logFood(
        token: String,
        date: LocalDate,
        food: String,
        calories: Int,
        proteinGrams: Int,
        fatsGrams: Int,
        carbsGrams: Int,
    ) {
        val id = client.rpc {
            this.logFood(
                token = token,
                date = date,
                food = food,
                calories = calories,
                proteinGrams = proteinGrams,
                fatsGrams = fatsGrams,
                carbsGrams = carbsGrams,
            )
        }
        
        store.update {
            val map = it?.logs?.toMutableMap() ?: mutableMapOf()
            val list = map[date]?.toMutableList() ?: mutableListOf()
            list += FoodLog(
                id = id,
                food = food,
                calories = calories,
                proteinGrams = proteinGrams,
                carbsGrams = carbsGrams,
                fatsGrams = fatsGrams,
            )
            map[date] = list
            it?.copy(logs = map)
        }
    }
    
    suspend fun removeFoodLog(token: String, date: LocalDate, id: Int) {
        client.rpc {
            this.removeFoodLog(token, id)
        }
        
        store.update {
            it ?: return@update it
            
            val map = it.logs.toMutableMap()
            val list = map[date]?.toMutableList() ?: return@update it
            list.removeAll { l -> l.id == id }
            map[date] = list
            
            it.copy(logs = map)
        }
    }
}

@Serializable
data class FoodLogs(
    val logs: Map<LocalDate, List<FoodLog>> = emptyMap(),
    val calorieGoal: Int = 2000,
    val proteinGoal: Int = 125,
    val carbsGoal: Int = 225,
    val fatGoal: Int = 67,
)
