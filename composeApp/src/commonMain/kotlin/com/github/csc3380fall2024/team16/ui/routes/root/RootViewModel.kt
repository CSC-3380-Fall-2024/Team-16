package com.github.csc3380fall2024.team16.ui.routes.root

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed interface RootState {
    data object LoggedOut : RootState
    data class LoggedIn(val token: String) : RootState
}

class RootViewModel : ViewModel() {
    var state: RootState by mutableStateOf(RootState.LoggedOut)
        private set
    
    fun onAuthenticated(token: String) {
        state = RootState.LoggedIn(token)
    }
}
