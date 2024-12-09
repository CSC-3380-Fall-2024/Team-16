package com.github.csc3380fall2024.team16.ui.routes.root

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.repository.SessionRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

sealed interface RootState {
    data object LoggedOut : RootState
    data class LoggedIn(val token: String) : RootState
}

class RootViewModel(private val sessionRepo: SessionRepository) : ViewModel() {
    var state: RootState by mutableStateOf(
        when (val session = runBlocking { sessionRepo.get() }) {
            null -> RootState.LoggedOut
            else -> RootState.LoggedIn(session.token)
        }
    )
        private set
    
    fun onAuthenticated(token: String) {
        viewModelScope.launch { sessionRepo.set(token) }
        state = RootState.LoggedIn(token)
    }
}
