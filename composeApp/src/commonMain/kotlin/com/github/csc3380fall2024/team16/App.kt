package com.github.csc3380fall2024.team16

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.repository.SessionRepository
import com.github.csc3380fall2024.team16.ui.routes.root.RootRoute
import com.github.csc3380fall2024.team16.ui.theme.AppTheme
import kotlinx.io.files.Path

@Composable
fun App(appDir: String) {
    val viewModel = viewModel { AppViewModel(appDir) }
    
    AppTheme(dark = true) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            RootRoute(viewModel.client, viewModel.sessionRepo)
        }
    }
}

class AppViewModel(appDir: String) : ViewModel() {
    val client = RpcClient()
    val sessionRepo = SessionRepository(Path(appDir, "session"))
}
