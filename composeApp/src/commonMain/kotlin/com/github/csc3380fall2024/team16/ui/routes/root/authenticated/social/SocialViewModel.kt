package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel

class SocialViewModel : ViewModel() {
    var name: String? by mutableStateOf(null)
    var bio: String? by mutableStateOf(null)
    var profilePicture: Painter? by mutableStateOf(null)
}
