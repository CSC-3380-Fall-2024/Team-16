package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.serialization.Serializable

@Serializable
data class NewsRoute(val token: String)

@Composable
fun NewsRoute.compose(app: AppResources) {
    val articles by app.newsRepo.updates.collectAsState()
    
    val viewModel = viewModel { NewsViewModel(app.newsRepo, token) }
    LaunchedEffect(Unit) {
        viewModel.fetch("Fitness, exercise, and lifting", "Sports", "Health and body")
    }
    NewsScreen(
        articles = articles,
        error = viewModel.error,
    )
}
