package com.github.csc3380fall2024.team16.repository

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path
import kotlinx.serialization.Serializable

class SessionRepository(path: Path) {
    private val store: KStore<Session> = storeOf(file = path)
    
    suspend fun get(): Session? {
        return store.get()
    }
    
    suspend fun set(token: String) {
        store.set(Session(token))
    }
}

@Serializable
data class Session(val token: String)
