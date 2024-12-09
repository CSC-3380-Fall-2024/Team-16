package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Composable
fun LoginRoute.compose(
    app: AppResources,
    onNavigateRegister: () -> Unit,
    onNavigateForgotPassword: () -> Unit,
) {
    val viewModel = viewModel { LoginViewModel(app.sessionRepo) }
    LoginScreen(
        state = viewModel.state,
        onLogin = viewModel::login,
        onNavigateRegister = onNavigateRegister,
        onNavigateForgotPassword = onNavigateForgotPassword,
    )
}
