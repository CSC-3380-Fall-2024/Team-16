package com.github.csc3380fall2024.team16.repository

import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.model.NewsArticle
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.extensions.updatesOrEmpty
import io.github.xxfast.kstore.file.extensions.listStoreOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.io.files.Path

class NewsRepository(private val client: RpcClient, path: Path) {
    private val store: KStore<List<Pair<String, List<NewsArticle>>>> = listStoreOf(file = path)
    
    val updates = store.updatesOrEmpty.stateIn(CoroutineScope(Job()), SharingStarted.Eagerly, emptyList())
    
    suspend fun fetch(token: String, vararg queries: String) = coroutineScope {
        val tasks = queries.map { query ->
            async {
                query to client.rpc { getNewsArticles(token, query) }
            }
        }
        
        store.set(tasks.awaitAll())
    }
}
