package com.github.csc3380fall2024.team16.ui.routes.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import com.github.csc3380fall2024.team16.Navigator
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.AuthenticatedRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.compose
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.UnauthenticatedRoute
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.compose

@Composable
fun RootRoute(app: AppResources) {
    val viewModel = viewModel { RootViewModel(app.sessionRepo) }
    Navigator(
        start = when (val state = viewModel.state) {
            is RootState.LoggedOut -> UnauthenticatedRoute
            is RootState.LoggedIn  -> AuthenticatedRoute(state.token)
        },
        effect = {
            LaunchedEffect(viewModel.state) {
                when (val state = viewModel.state) {
                    is RootState.LoggedOut -> navController.navigate(UnauthenticatedRoute) { popUpTo(0) }
                    is RootState.LoggedIn  -> navController.navigate(AuthenticatedRoute(state.token)) { popUpTo(0) }
                }
            }
        }
    ) {
        route<UnauthenticatedRoute> {
            this.compose(
                client = app.client,
                onAuthenticated = { viewModel.onAuthenticated(it) }
            )
        }
        
        route<AuthenticatedRoute> { this.compose(client = app.client) }
    }
}


