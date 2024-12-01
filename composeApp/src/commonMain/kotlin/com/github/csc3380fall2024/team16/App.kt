package com.github.csc3380fall2024.team16

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.ui.pages.Authenticated
import com.github.csc3380fall2024.team16.ui.pages.AuthenticatedRoute
import com.github.csc3380fall2024.team16.ui.pages.Unauthenticated
import com.github.csc3380fall2024.team16.ui.pages.UnauthenticatedRoute
import com.github.csc3380fall2024.team16.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme(dark = true) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val client = remember { RpcClient() }
            val viewModel = viewModel { AppViewModel() }
            
            Navigator(
                start = Unauthenticated,
                effect = {
                    LaunchedEffect(viewModel.state) {
                        when (val state = viewModel.state) {
                            is AppState.LoggedOut -> navController.navigate(Unauthenticated) { popUpTo(0) }
                            is AppState.LoggedIn  -> navController.navigate(Authenticated(state.token)) { popUpTo(0) }
                        }
                    }
                }
            ) {
                route<Unauthenticated> {
                    UnauthenticatedRoute(
                        client = client,
                        onAuthenticated = { viewModel.onAuthenticated(it) },
                    )
                }
                
                route<Authenticated> {
                    AuthenticatedRoute(
                        client = client,
                        token = it.token,
                    )
                }
            }
        }
    }
}
