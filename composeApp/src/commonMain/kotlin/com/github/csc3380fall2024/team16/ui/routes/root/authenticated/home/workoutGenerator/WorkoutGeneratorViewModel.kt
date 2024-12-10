package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutGenerator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.repository.WorkoutLogsRepository
import kotlinx.coroutines.launch

class WorkoutGeneratorViewModel(private val workoutLogRepo: WorkoutLogsRepository, private val token: String) : ViewModel() {
    var error by mutableStateOf(false)
        private set
    
    fun addWorkoutLog(goal: String, target: String, intensity: String) = viewModelScope.launch {
        try {
            workoutLogRepo.logWorkout(token, goal, target, intensity)
            error = false
        } catch (e: Exception) {
            error = true
        }
    }
}
