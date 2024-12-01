package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutGenerator

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object WorkoutGeneratorRoute

@Composable
fun WorkoutGeneratorRoute.compose(navController: NavController) {
    WorkoutGeneratorScreen(navController)
}
