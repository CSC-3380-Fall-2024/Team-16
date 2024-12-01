package com.github.csc3380fall2024.team16

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun CustomNavHost(navController: NavHostController, startDestination: Any, builder: CustomNavGraphBuilder.() -> Unit) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        CustomNavGraphBuilder(this).builder()
    }
}

class CustomNavGraphBuilder(val host: NavGraphBuilder) {
    inline fun <reified T : Any> composable(noinline content: @Composable (T) -> Unit) {
        host.composable<T> {
            content(it.toRoute<T>())
        }
    }
}
