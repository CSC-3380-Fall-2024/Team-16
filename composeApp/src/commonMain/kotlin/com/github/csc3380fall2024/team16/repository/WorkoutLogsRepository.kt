package com.github.csc3380fall2024.team16.repository

import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.WorkoutLog
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.extensions.getOrEmpty
import io.github.xxfast.kstore.extensions.plus
import io.github.xxfast.kstore.file.extensions.listStoreOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.io.files.Path

class WorkoutLogsRepository(private val client: RpcClient, path: Path) {
    private val store: KStore<List<WorkoutLog>> = listStoreOf(file = path)
    
    val updates =
        store.updates.stateIn(CoroutineScope(Job()), SharingStarted.Eagerly, runBlocking { store.getOrEmpty() })
    
    suspend fun logWorkout(token: String, goal: String, target: String, intensity: String) {
        val log = """
            Goal: $goal
            Target: $target
            Intensity: $intensity
        """.trimIndent()
        
        store.plus(WorkoutLog(log, Clock.System.now()))
        client.rpc { this.logWorkout(token, log) }
    }
    
    suspend fun fetch(token: String) {
        val logs = client.rpc { getWorkoutLogs(token) }
        store.set(logs)
    }
}
