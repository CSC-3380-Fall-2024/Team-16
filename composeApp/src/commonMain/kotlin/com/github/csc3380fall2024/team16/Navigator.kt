package com.github.csc3380fall2024.team16

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

/**
 * A cleaner alternative to default compose navigation.
 *
 * The default compose navigation library has some problems.
 * It doesn't support passing parameters to subgraphs in a clean way.
 * You must instead create nested NavHosts to achieve that,
 * but then each NavHost needs its own navController, making code repetitive.
 * Also, each route needs to call toRoute() to retrieve its parameters, making code repetitive.
 *
 * This is a component built on top of default compose navigation to address these issues.
 *
 * @param start The default route.
 * @param compose A composable function that renders the underlying NavHost.
 * Useful for wrapping the composition of the underlying NavHost if the logic depends on the underlying navController.
 * The function has a single parameter, which is itself a composable function, that renders the NavHost when called.
 * That function must be called exactly once.
 * @param effect A composable function for logic dependent on the underlying navController.
 * Useful for side effects that depend on the underlying navController.
 * Called directly after [compose] is called.
 * Even though [compose] can achieve the same, it requires calling the underlying composition, which you might
 * forget when no custom composition logic is needed. That's why this [effect] exists.
 * @param builder The builder used to define routes.
 */
@Composable
fun Navigator(
    start: Any,
    compose: @Composable NavHostComposer.(@Composable () -> Unit) -> Unit = { it() },
    effect: @Composable NavHostComposer.() -> Unit = { },
    builder: RoutesBuilder.() -> Unit,
) {
    val navController = rememberNavController()
    val composer = NavHostComposer(navController)
    composer.compose {
        NavHost(
            navController = navController,
            startDestination = start,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            RoutesBuilder(this, navController).builder()
        }
    }
    composer.effect()
}

class NavHostComposer(val navController: NavController)

class RoutesBuilder(val underlyingNavGraphBuilder: NavGraphBuilder, val navController: NavController) {
    inline fun <reified T : Any> route(noinline content: @Composable (T) -> Unit) {
        underlyingNavGraphBuilder.composable<T> {
            content(it.toRoute<T>())
        }
    }
}
