package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.tracker

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object TrackerRoute

@Composable
fun TrackerRoute.compose(currentCalories: Int, calorieGoal: Int) {
    TrackerScreen(currentCalories, calorieGoal)
}
