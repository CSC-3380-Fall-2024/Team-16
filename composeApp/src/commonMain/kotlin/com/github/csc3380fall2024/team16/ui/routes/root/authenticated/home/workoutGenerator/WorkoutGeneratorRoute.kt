package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutGenerator

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.serialization.Serializable

@Serializable
object WorkoutGeneratorRoute

@Composable
fun WorkoutGeneratorRoute.compose(app: AppResources, token: String, onBack: () -> Unit) {
    val viewModel = viewModel { WorkoutGeneratorViewModel(app.workoutLogsRepo, token) }
    
    WorkoutGeneratorScreen(
        onLogWorkout = viewModel::addWorkoutLog,
        onBack = onBack,
    )
}
