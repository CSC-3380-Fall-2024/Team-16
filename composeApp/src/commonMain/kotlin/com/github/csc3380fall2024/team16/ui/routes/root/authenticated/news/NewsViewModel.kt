package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.RpcResult
import com.github.csc3380fall2024.team16.model.NewsArticle
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

sealed interface NewsState {
    data object Loading : NewsState
    data class Loaded(val articles: List<Pair<String, List<NewsArticle>>>) : NewsState
    data class Error(val exception: Exception) : NewsState
}

class NewsViewModel(private val client: RpcClient, private val token: String) : ViewModel() {
    var state: NewsState by mutableStateOf(NewsState.Loading)
        private set
    
    init {
        fetch("Fitness, excercise, and lifting", "Sports")
    }
    
    private fun fetch(vararg queries: String) {
        val tasks = queries.map { query ->
            query to viewModelScope.async {
                client.rpc { getNewsArticles(token, query) }
            }
        }
        
        viewModelScope.launch {
            val responses = tasks.map { it.first to it.second.await() }
            val err = responses.find { it.second is RpcResult.ConnectionFailure }
            state = if (err == null) {
                NewsState.Loaded(responses.map { it.first to (it.second as RpcResult.Success).value })
            } else {
                NewsState.Error((err.second as RpcResult.ConnectionFailure).exception)
            }
        }
    }
}
