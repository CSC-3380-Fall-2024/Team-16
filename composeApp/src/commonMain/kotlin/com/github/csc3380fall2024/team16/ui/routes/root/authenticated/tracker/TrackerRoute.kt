package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.tracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object TrackerRoute

@Composable
fun TrackerRoute.compose(navController: NavController, currentCalories: Int, calorieGoal: Int) {
    TrackerScreen(navController, currentCalories, calorieGoal)
}
