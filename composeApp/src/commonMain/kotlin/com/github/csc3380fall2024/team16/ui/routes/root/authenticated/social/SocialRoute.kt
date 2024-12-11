package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.serialization.Serializable

@Serializable
object SocialRoute

@Composable
fun SocialRoute.compose(app: AppResources, token: String) {
    val session by app.sessionRepo.updates.collectAsState()
    
    val viewModel = viewModel { SocialViewModel(token, app.postsRepo) }
    
    session?.let {
        SocialScreen(
            name = it.username,
            profilePicture = null,
            onCreatePost = viewModel::createPost,
        )
    }
}
