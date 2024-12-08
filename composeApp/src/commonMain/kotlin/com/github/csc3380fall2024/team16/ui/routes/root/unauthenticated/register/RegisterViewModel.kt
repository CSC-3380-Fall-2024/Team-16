package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.AlreadyExistsException
import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.ValidationException
import kotlinx.coroutines.launch

sealed interface RegisterState {
    data class Ready(val lastError: String?) : RegisterState
    data object InProgress : RegisterState
    data class Success(val token: String) : RegisterState
}

class RegisterViewModel(private val client: RpcClient) : ViewModel() {
    var state: RegisterState by mutableStateOf(RegisterState.Ready(null))
        private set
    
    fun register(username: String, email: String, password: String, confirmPassword: String) = viewModelScope.launch {
        if (password != confirmPassword) {
            state = RegisterState.Ready("passwords do not match")
            return@launch
        }
        
        state = RegisterState.InProgress
        
        try {
            val token = client.rpc { register(username, email, password) }
            state = RegisterState.Success(token)
        } catch (e: ValidationException) {
            state = RegisterState.Ready(e.message)
        } catch (e: AlreadyExistsException) {
            state = RegisterState.Ready(e.message)
        } catch (_: Exception) {
            state = RegisterState.Ready("error")
        }
    }
}
