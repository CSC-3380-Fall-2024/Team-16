package com.github.csc3380fall2024.team16.ui.routes.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.csc3380fall2024.team16.AppResources
import com.github.csc3380fall2024.team16.Navigator
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.AuthenticatedRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.compose
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.UnauthenticatedRoute
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.compose

@Composable
fun RootRoute(app: AppResources) {
    val state by app.sessionRepo.updates.collectAsState()
    
    Navigator(
        start = when (val session = state) {
            null -> UnauthenticatedRoute
            else -> AuthenticatedRoute(session.token)
        },
        effect = {
            when (val session = state) {
                null -> navController.navigate(UnauthenticatedRoute) { popUpTo(0) }
                else -> navController.navigate(AuthenticatedRoute(session.token)) { popUpTo(0) }
            }
        }
    ) {
        route<UnauthenticatedRoute> { this.compose(app) }
        route<AuthenticatedRoute> { this.compose(app) }
    }
}


