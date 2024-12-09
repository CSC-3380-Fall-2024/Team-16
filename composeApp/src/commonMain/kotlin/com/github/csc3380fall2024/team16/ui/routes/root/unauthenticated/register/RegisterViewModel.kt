package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.AlreadyExistsException
import com.github.csc3380fall2024.team16.NetworkException
import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.ValidationException
import kotlinx.coroutines.launch

sealed interface RegisterState {
    data class Ready(val lastError: String?) : RegisterState
    data object InProgress : RegisterState
    data object Success : RegisterState
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
            client.rpc { register(username, email, password) }
            state = RegisterState.Success
        } catch (e: ValidationException) {
            state = RegisterState.Ready(e.message)
        } catch (e: AlreadyExistsException) {
            state = RegisterState.Ready(e.message)
        } catch (e: NetworkException) {
            state = RegisterState.Ready("could not reach server")
        } catch (e: Exception) {
            state = RegisterState.Ready("unknown error")
        }
    }
}
