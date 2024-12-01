package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated

import androidx.compose.runtime.Composable
import com.github.csc3380fall2024.team16.Navigator
import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.forgotPassword.ForgotPasswordRoute
import com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.forgotPassword.compose
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
fun UnauthenticatedRoute.compose(
    client: RpcClient,
    onAuthenticated: (token: String) -> Unit,
) {
    Navigator(start = WelcomeRoute) {
        route<WelcomeRoute> { this.compose(navController) }
        route<RegisterRoute> { this.compose(client, onAuthenticated) }
        route<LoginRoute> {
            this.compose(
                client = client,
                onAuthenticated = onAuthenticated,
                onNavigateRegister = { navController.navigate(RegisterRoute) },
                onNavigateForgotPassword = { navController.navigate(ForgotPasswordRoute) },
            )
        }
        route<ForgotPasswordRoute> { this.compose(navController) }
    }
}
