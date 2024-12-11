package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.repository.PostsRepository
import kotlinx.coroutines.launch

class SocialViewModel(private val token: String, private val postsRepo: PostsRepository) : ViewModel() {
    var error: String? by mutableStateOf(null)
        private set
    
    fun createPost(description: String, image: ByteArray) = viewModelScope.launch {
        try {
            postsRepo.createPost(token, description, image)
            error = null
        } catch (e: Exception) {
            error = "There was an error creating a post."
        }
    }
}
