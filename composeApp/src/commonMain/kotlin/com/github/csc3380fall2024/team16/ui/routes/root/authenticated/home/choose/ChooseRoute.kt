package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.choose

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.ui.pages.home.ChoosePage
import kotlinx.serialization.Serializable

@Serializable
object ChooseRoute

@Composable
fun ChooseRoute.compose(navController: NavController) {
    ChoosePage(navController)
}
