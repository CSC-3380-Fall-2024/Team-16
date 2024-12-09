package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.news

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.serialization.Serializable

@Serializable
data class NewsRoute(val token: String)

@Composable
fun NewsRoute.compose(app: AppResources) {
    val viewModel = viewModel { NewsViewModel(app.client, token) }
    NewsScreen(viewModel.state)
}
