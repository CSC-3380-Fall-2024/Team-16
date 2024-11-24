package com.github.csc3380fall2024.team16.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.csc3380fall2024.team16.RpcClient

class MainViewModel : ViewModel() {
    private val client = RpcClient()
    private var token: String? by mutableStateOf(null)
    
    val isLoggedIn by derivedStateOf { token != null }
    
    suspend fun register(username: String, email: String, password: String) {
        client.rpc {
            token = register(username, email, password)
        }
    }
    
    suspend fun login(usernameOrEmail: String, password: String) {
        client.rpc {
            token = login(usernameOrEmail, password)
        }
    }
}
