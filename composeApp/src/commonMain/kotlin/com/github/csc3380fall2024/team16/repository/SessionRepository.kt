package com.github.csc3380fall2024.team16.repository

import com.github.csc3380fall2024.team16.RpcClient
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.io.files.Path
import kotlinx.serialization.Serializable

class SessionRepository(private val client: RpcClient, path: Path) {
    private val store: KStore<Session> = storeOf(file = path)
    
    val updates = store.updates.stateIn(CoroutineScope(Job()), SharingStarted.Eagerly, runBlocking { store.get() })
    
    suspend fun create(usernameOrEmail: String, password: String) {
        val token = client.rpc { login(usernameOrEmail, password) }
        store.set(Session(token))
    }
}

@Serializable
data class Session(val token: String)
