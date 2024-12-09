package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.NetworkException
import com.github.csc3380fall2024.team16.UnauthorizedException
import com.github.csc3380fall2024.team16.ValidationException
import com.github.csc3380fall2024.team16.repository.SessionRepository
import kotlinx.coroutines.launch

sealed interface LoginState {
    data class Ready(val lastError: String?) : LoginState
    data object InProgress : LoginState
}

class LoginViewModel(private val sessionRepo: SessionRepository) : ViewModel() {
    var state: LoginState by mutableStateOf(LoginState.Ready(null))
        private set
    
    fun login(usernameOrEmail: String, password: String) = viewModelScope.launch {
        state = LoginState.InProgress
        
        try {
            sessionRepo.create(usernameOrEmail, password)
        } catch (e: ValidationException) {
            state = LoginState.Ready(e.message)
        } catch (e: UnauthorizedException) {
            state = LoginState.Ready("invalid credentials")
        } catch (e: NetworkException) {
            state = LoginState.Ready("could not reach server")
        } catch (e: Exception) {
            state = LoginState.Ready("unknown error")
        }
    }
}
