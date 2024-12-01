package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.welcome

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.ui.pages.WelcomePage
import kotlinx.serialization.Serializable

@Serializable
object WelcomeRoute

@Composable
fun WelcomeRoute.compose(navController: NavController) {
    WelcomePage(navController)
}
