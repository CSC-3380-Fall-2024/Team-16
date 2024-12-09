package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.choose

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object ChooseRoute

@Composable
fun ChooseRoute.compose(
    username: String,
    onNavigateWorkoutGenerator: () -> Unit,
    onNavigateBodyBuilding: () -> Unit,
    onNavigatePowerlifting: () -> Unit,
    onNavigateAthletics: () -> Unit,
    onNavigateWeightloss: () -> Unit,
) {
    ChooseScreen(
        username,
        onNavigateWorkoutGenerator,
        onNavigateBodyBuilding,
        onNavigatePowerlifting,
        onNavigateAthletics,
        onNavigateWeightloss,
    )
}
