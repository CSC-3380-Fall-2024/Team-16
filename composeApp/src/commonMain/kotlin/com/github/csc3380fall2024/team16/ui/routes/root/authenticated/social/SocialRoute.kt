package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.serialization.Serializable

@Serializable
object SocialRoute

@Composable
fun SocialRoute.compose() {
    // Scope the ViewModel to the Navigation graph
    val viewModel: SocialViewModel = viewModel()
    SocialScreen(viewModel = viewModel)
}
