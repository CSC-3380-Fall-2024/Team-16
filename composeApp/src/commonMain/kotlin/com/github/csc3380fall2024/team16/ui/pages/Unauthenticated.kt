package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.Navigator
import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.RpcResult
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Unauthenticated

@Composable
fun UnauthenticatedRoute(
	client: RpcClient,
	onAuthenticated: (token: String) -> Unit,
) {
	Navigator(start = Welcome) {
		route<Welcome> { WelcomePage(navController) }
		route<Register> {
			val viewModel = viewModel { RegisterViewModel(client) }
			RegisterPage(onRegister = viewModel::register)
			LaunchedEffect(viewModel.token) {
				val token = viewModel.token
				if (token != null) onAuthenticated(token)
			}
		}
		route<Login> {
			val viewModel = viewModel { LoginViewModel(client) }
			LoginPage(
				onLogin = viewModel::login,
				onNavigateRegister = {
					navController.navigate(Register)
				},
				onNavigateForgotPassword = {
					navController.navigate(ForgotPassword)
				}
			)
			LaunchedEffect(viewModel.token) {
				val token = viewModel.token
				if (token != null) onAuthenticated(token)
			}
		}
		route<ForgotPassword> { ForgotPasswordPage(navController) }
	}
}

class RegisterViewModel(private val client: RpcClient) : ViewModel() {
	var error: String? by mutableStateOf(null)
		private set
	
	var token: String? by mutableStateOf(null)
		private set
	
	fun register(username: String, email: String, password: String) {
		viewModelScope.launch {
			when (val resp = client.rpc { register(username, email, password) }) {
				is RpcResult.ConnectionFailure -> error = resp.exception.toString()
				is RpcResult.Success           -> token = resp.value
			}
		}
	}
}

class LoginViewModel(private val client: RpcClient) : ViewModel() {
	var error: String? by mutableStateOf(null)
		private set
	
	var token: String? by mutableStateOf(null)
		private set
	
	fun login(usernameOrEmail: String, password: String) {
		viewModelScope.launch {
			when (val resp = client.rpc { login(usernameOrEmail, password) }) {
				is RpcResult.ConnectionFailure -> error = resp.exception.toString()
				is RpcResult.Success           -> token = resp.value
			}
		}
	}
}
