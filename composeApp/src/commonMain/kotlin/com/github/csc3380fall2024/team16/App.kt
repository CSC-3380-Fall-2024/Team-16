package com.github.csc3380fall2024.team16

import UniversalFitness.composeApp.BuildConfig
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.ui.routes.root.RootRoute
import com.github.csc3380fall2024.team16.ui.theme.AppTheme
import io.ktor.http.URLBuilder
import io.ktor.http.Url

@Composable
fun App(appDir: String) {
    val viewModel = viewModel {
        AppViewModel(
            backendUrl = URLBuilder(host = BuildConfig.SERVER_IP, port = BuildConfig.SERVER_PORT).build(),
            appDir = appDir,
        )
    }
    
    AppTheme(dark = true) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            RootRoute(viewModel.app)
        }
    }
}

class AppViewModel(backendUrl: Url, appDir: String) : ViewModel() {
    val app = AppResources(backendUrl, appDir)
}
