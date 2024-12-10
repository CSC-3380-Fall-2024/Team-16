package com.github.csc3380fall2024.team16.repository

import com.github.csc3380fall2024.team16.Post
import com.github.csc3380fall2024.team16.RpcClient
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.extensions.getOrEmpty
import io.github.xxfast.kstore.file.extensions.listStoreOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.io.files.Path

class PostsRepository(private val client: RpcClient, path: Path) {
    private val store: KStore<List<Post>> = listStoreOf(file = path)
    
    val updates =
        store.updates.stateIn(CoroutineScope(Job()), SharingStarted.Eagerly, runBlocking { store.getOrEmpty() })
    
    suspend fun fetch(token: String) {
        val posts = client.rpc { getPosts(token) }
        store.set(posts)
    }
    
    suspend fun createPost(token: String, description: String, image: ByteArray) {
        client.rpc { addPost(token, description, image) }
        fetch(token)
    }
}
