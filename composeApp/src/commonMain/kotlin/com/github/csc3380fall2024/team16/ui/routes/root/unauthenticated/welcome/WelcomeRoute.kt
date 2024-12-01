package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.welcome

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object WelcomeRoute

@Composable
fun WelcomeRoute.compose(onNavigateRegister: () -> Unit, onNavigateLogin: () -> Unit) {
    WelcomeScreen(onNavigateRegister, onNavigateLogin)
}
