package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object SocialRoute

@Composable
fun SocialRoute.compose(app: AppResources, token: String) {
    val session by app.sessionRepo.updates.collectAsState()
    val posts by app.postsRepo.updates.collectAsState()
    
    val viewModel = viewModel { SocialViewModel(token, app.client, app.postsRepo) }
    
    LaunchedEffect(Unit) { viewModel.fetchPosts() }
    
    session?.let {
        SocialScreen(
            name = it.username,
            profilePicture = null,
            posts = posts ?: emptyList(),
            onCreatePost = viewModel::createPost,
            backendUrl = app.backendUrl,
            onLogout = { viewModel.viewModelScope.launch { app.sessionRepo.logout() } },
            onUploadPfp = viewModel::uploadPfp,
            error = viewModel.error,
        )
    }
}
