package com.github.csc3380fall2024.team16

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed interface AppState {
    data object LoggedOut : AppState
    data class LoggedIn(val token: String) : AppState
}

class AppViewModel : ViewModel() {
    var state: AppState by mutableStateOf(AppState.LoggedOut)
        private set
    
    fun onAuthenticated(token: String) {
        state = AppState.LoggedIn(token)
    }
}
