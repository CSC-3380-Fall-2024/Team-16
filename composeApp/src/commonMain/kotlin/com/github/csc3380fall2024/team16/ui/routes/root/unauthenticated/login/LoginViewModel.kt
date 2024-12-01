package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.RpcResult
import kotlinx.coroutines.launch

class LoginViewModel(private val client: RpcClient) : ViewModel() {
    var error: String? by mutableStateOf(null)
        private set
    
    var token: String? by mutableStateOf(null)
        private set
    
    fun login(usernameOrEmail: String, password: String) {
        viewModelScope.launch {
            when (val resp = client.rpc { login(usernameOrEmail, password) }) {
                is RpcResult.ConnectionFailure -> error = resp.exception.toString()
                is RpcResult.Success           -> token = resp.value
            }
        }
    }
}
