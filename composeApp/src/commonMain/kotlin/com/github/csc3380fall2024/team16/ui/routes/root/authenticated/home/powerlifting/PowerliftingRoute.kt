package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.powerlifting

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object PowerliftingRoute

@Composable
fun PowerliftingRoute.compose(navController: NavController) {
    PowerliftingScreen(navController)
}
