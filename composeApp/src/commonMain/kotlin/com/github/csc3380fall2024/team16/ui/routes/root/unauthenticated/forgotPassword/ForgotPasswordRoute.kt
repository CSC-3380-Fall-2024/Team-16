package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.forgotPassword

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object ForgotPasswordRoute

@Composable
fun ForgotPasswordRoute.compose(navController: NavController) {
    ForgotPasswordScreen(navController)
}
