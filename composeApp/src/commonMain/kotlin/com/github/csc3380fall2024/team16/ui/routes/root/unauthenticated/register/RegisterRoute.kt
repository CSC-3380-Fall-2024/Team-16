package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.register

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.serialization.Serializable

@Serializable
object RegisterRoute

@Composable
fun RegisterRoute.compose(
    app: AppResources,
    onNavigateLogin: () -> Unit
) {
    val viewModel = viewModel { RegisterViewModel(app.client) }
    RegisterScreen(
        state = viewModel.state,
        onRegister = viewModel::register,
        onNavigateLogin = onNavigateLogin,
    )
}
