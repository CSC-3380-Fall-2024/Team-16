package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.news

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.ui.pages.NewsPage
import kotlinx.serialization.Serializable

@Serializable
object NewsRoute

@Composable
fun NewsRoute.compose(navController: NavController) {
    NewsPage(navController)
}
