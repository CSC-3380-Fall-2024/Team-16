package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.RpcClient
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Composable
fun LoginRoute.compose(
    client: RpcClient,
    onAuthenticated: (String) -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateForgotPassword: () -> Unit,
) {
    val viewModel = viewModel { LoginViewModel(client) }
    LoginScreen(
        state = viewModel.state,
        onLogin = viewModel::login,
        onNavigateRegister = onNavigateRegister,
        onNavigateForgotPassword = onNavigateForgotPassword,
    )
    LaunchedEffect(viewModel.state) {
        viewModel.state.let {
            if (it is LoginState.Success) onAuthenticated(it.token)
        }
    }
}
