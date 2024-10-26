package com.github.csc3380fall2024.team16

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.csc3380fall2024.team16.ui.pages.AthleticsPage
import com.github.csc3380fall2024.team16.ui.pages.BodybuildingPage
import com.github.csc3380fall2024.team16.ui.pages.HomeScreen
import com.github.csc3380fall2024.team16.ui.pages.PowerliftingPage
import com.github.csc3380fall2024.team16.ui.pages.RegisterScreen
import com.github.csc3380fall2024.team16.ui.pages.WeightLossPage
import com.github.csc3380fall2024.team16.ui.pages.Login
import com.github.csc3380fall2024.team16.ui.pages.ForgotPassword
import com.github.csc3380fall2024.team16.ui.theme.AppTheme

@Composable
fun App() {
    val navController = rememberNavController()
    
    AppTheme(dark = true) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController, startDestination = "register") {
                composable("register") { RegisterScreen(navController) }
                composable("forgotpassword") { ForgotPassword(navController) }
                composable("login") { Login(navController) }
                composable("home") { HomeScreen(navController) }
                composable("bodybuilding") { BodybuildingPage(navController) }
                composable("powerlifting") { PowerliftingPage(navController) }
                composable("athletics") { AthleticsPage(navController) }
                composable("weightloss") { WeightLossPage(navController) }
            }
        }
    }
}





