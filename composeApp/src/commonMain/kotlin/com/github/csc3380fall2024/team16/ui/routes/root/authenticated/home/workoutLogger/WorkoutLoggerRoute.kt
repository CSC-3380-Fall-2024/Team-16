package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutLogger

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object WorkoutLoggerRoute

@Composable
fun WorkoutLoggerRoute.compose(onBack: () -> Unit) {
    WorkoutLoggerScreen(onBack)
}
