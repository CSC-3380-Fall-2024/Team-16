package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.athletics

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object AthleticsRoute

@Composable
fun AthleticsRoute.compose(navController: NavController) {
    AthleticsScreen(navController)
}
