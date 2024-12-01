package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.bodybuilding

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object BodybuildingRoute

@Composable
fun BodybuildingRoute.compose(navController: NavController) {
    BodybuildingScreen(navController)
}
