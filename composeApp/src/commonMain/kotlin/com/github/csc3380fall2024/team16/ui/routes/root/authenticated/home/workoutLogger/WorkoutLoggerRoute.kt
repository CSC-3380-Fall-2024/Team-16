package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutLogger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.serialization.Serializable

@Serializable
object WorkoutLoggerRoute

@Composable
fun WorkoutLoggerRoute.compose(app: AppResources, token: String, onBack: () -> Unit) {
    val workoutLogs by app.workoutLogsRepo.updates.collectAsState()

    val viewModel = viewModel { WorkoutLoggerViewModel(app.workoutLogsRepo, token) }

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

    workoutLogs?.let {
        WorkoutLoggerScreen(
            foodLogs = it,
            onBack = onBack,
            error = viewModel.error,
        )
    }
}
