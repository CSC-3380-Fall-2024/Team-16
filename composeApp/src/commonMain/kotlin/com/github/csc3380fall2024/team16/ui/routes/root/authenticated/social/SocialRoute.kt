package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.ui.pages.SocialPage
import kotlinx.serialization.Serializable

@Serializable
object SocialRoute

@Composable
fun SocialRoute.compose(navController: NavController) {
    SocialPage(navController)
}
