package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.RpcClient
import kotlinx.serialization.Serializable

@Serializable
object RegisterRoute

@Composable
fun RegisterRoute.compose(client: RpcClient, onAuthenticated: (String) -> Unit) {
    val viewModel = viewModel { RegisterViewModel(client) }
    RegisterScreen(onRegister = viewModel::register)
    LaunchedEffect(viewModel.token) {
        val token = viewModel.token
        if (token != null) onAuthenticated(token)
    }
}
