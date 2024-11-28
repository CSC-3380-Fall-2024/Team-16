package com.github.csc3380fall2024.team16

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.github.csc3380fall2024.team16.ui.components.BottomBar
import com.github.csc3380fall2024.team16.ui.components.Tab
import com.github.csc3380fall2024.team16.ui.pages.Add
import com.github.csc3380fall2024.team16.ui.pages.AddFood
import com.github.csc3380fall2024.team16.ui.pages.ForgotPassword
import com.github.csc3380fall2024.team16.ui.pages.ForgotPasswordPage
import com.github.csc3380fall2024.team16.ui.pages.Home
import com.github.csc3380fall2024.team16.ui.pages.Login
import com.github.csc3380fall2024.team16.ui.pages.LoginPage
import com.github.csc3380fall2024.team16.ui.pages.News
import com.github.csc3380fall2024.team16.ui.pages.NewsPage
import com.github.csc3380fall2024.team16.ui.pages.Register
import com.github.csc3380fall2024.team16.ui.pages.RegisterPage
import com.github.csc3380fall2024.team16.ui.pages.Social
import com.github.csc3380fall2024.team16.ui.pages.SocialPage
import com.github.csc3380fall2024.team16.ui.pages.Tracker
import com.github.csc3380fall2024.team16.ui.pages.TrackerPage
import com.github.csc3380fall2024.team16.ui.pages.Welcome
import com.github.csc3380fall2024.team16.ui.pages.WelcomePage
import com.github.csc3380fall2024.team16.ui.pages.home.Athletics
import com.github.csc3380fall2024.team16.ui.pages.home.AthleticsPage
import com.github.csc3380fall2024.team16.ui.pages.home.Bodybuilding
import com.github.csc3380fall2024.team16.ui.pages.home.BodybuildingPage
import com.github.csc3380fall2024.team16.ui.pages.home.Choose
import com.github.csc3380fall2024.team16.ui.pages.home.ChoosePage
import com.github.csc3380fall2024.team16.ui.pages.home.Powerlifting
import com.github.csc3380fall2024.team16.ui.pages.home.PowerliftingPage
import com.github.csc3380fall2024.team16.ui.pages.home.WeightLoss
import com.github.csc3380fall2024.team16.ui.pages.home.WeightLossPage
import com.github.csc3380fall2024.team16.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun App() {
    val client = remember { RpcClient() }
    val viewModel = viewModel { AppViewModel() }
    
    val navController = rememberNavController()
    val tabs = listOf(
        Tab("Home", Icons.Filled.Home, Home),
        Tab("Tracker", Icons.Filled.InsertChart, Tracker),
        Tab("News", Icons.Filled.Newspaper, News),
        Tab("Social", Icons.Filled.Group, Social),
    )
    
    AppTheme(dark = true) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Scaffold(bottomBar = { BottomBar(navController, tabs) }) {
                NavHost(
                    navController = navController,
                    startDestination = Welcome,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                ) {
                    composable<Welcome> { WelcomePage(navController) }
                    composable<Register> {
                        RegisterPage(
                            onRegister = { username, email, password ->
                                viewModel.viewModelScope.launch {
                                    when (val resp = client.rpc { register(username, email, password) }) {
                                        is RpcResult.ConnectionFailure -> {}
                                        is RpcResult.Success           -> viewModel.onAuthenticated(resp.value)
                                    }
                                }
                            }
                        )
                    }
                    composable<Login> {
                        LoginPage(
                            onLogin = { usernameOrEmail, password ->
                                viewModel.viewModelScope.launch {
                                    when (val resp = client.rpc { login(usernameOrEmail, password) }) {
                                        is RpcResult.ConnectionFailure -> {}
                                        is RpcResult.Success           -> viewModel.onAuthenticated(resp.value)
                                    }
                                }
                            },
                            onNavigateRegister = {
                                navController.navigate(Register)
                            },
                            onNavigateForgotPassword = {
                                navController.navigate(ForgotPassword)
                            }
                        )
                    }
                    composable<ForgotPassword> { ForgotPasswordPage(navController) }
                    
                    navigation<Home>(Choose) {
                        composable<Choose> { ChoosePage(navController) }
                        composable<Bodybuilding> { BodybuildingPage(navController) }
                        composable<Powerlifting> { PowerliftingPage(navController) }
                        composable<Athletics> { AthleticsPage(navController) }
                        composable<WeightLoss> { WeightLossPage(navController) }
                    }
                    composable<Tracker> { TrackerPage(navController, currentCalories = 1200f, calorieGoal = 2000f) }
                    composable<News> { NewsPage(navController) }
                    composable<Social> { SocialPage(navController) }
                    composable<Add> { AddFood(navController) }
                }
            }
        }
    }
    
    LaunchedEffect(viewModel.state) {
        when (viewModel.state) {
            is AppState.LoggedOut -> navController.navigate(Welcome)
            is AppState.LoggedIn  -> navController.navigate(Home)
        }
    }
}
