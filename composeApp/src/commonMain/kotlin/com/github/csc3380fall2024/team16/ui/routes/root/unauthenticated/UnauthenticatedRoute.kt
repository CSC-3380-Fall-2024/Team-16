package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated

import androidx.compose.runtime.Composable
import com.github.csc3380fall2024.team16.AppResources
import com.github.csc3380fall2024.team16.Navigator
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.login.LoginRoute
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.login.compose
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.register.RegisterRoute
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.register.compose
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.welcome.WelcomeRoute
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.welcome.compose
import kotlinx.serialization.Serializable

@Serializable
object UnauthenticatedRoute

@Composable
fun UnauthenticatedRoute.compose(app: AppResources) {
    Navigator(start = WelcomeRoute) {
        route<WelcomeRoute> {
            this.compose(
                onNavigateRegister = { navController.navigate(RegisterRoute) },
                onNavigateLogin = { navController.navigate(LoginRoute) }
            )
        }
        route<RegisterRoute> { this.compose(app) }
        route<LoginRoute> {
            this.compose(
                app = app,
                onNavigateRegister = { navController.navigate(RegisterRoute) },
            )
        }
    }
}
