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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.github.csc3380fall2024.team16.ui.components.BottomBar
import com.github.csc3380fall2024.team16.ui.components.Tab
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


@Composable
fun App() {
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
                    composable<Register> { RegisterPage(navController) }
                    composable<Login> { LoginPage(navController) }
                    composable<ForgotPassword> { ForgotPasswordPage(navController) }
                    
                    navigation<Home>(Choose) {
                        composable<Choose> { ChoosePage(navController) }
                        composable<Bodybuilding> { BodybuildingPage(navController) }
                        composable<Powerlifting> { PowerliftingPage(navController) }
                        composable<Athletics> { AthleticsPage(navController) }
                        composable<WeightLoss> { WeightLossPage(navController) }
                    }
                    composable<Tracker> { TrackerPage(navController) }
                    composable<News> { NewsPage(navController) }
                    composable<Social> { SocialPage(navController) }
                }
            }
        }
    }
}
