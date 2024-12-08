package com.github.csc3380fall2024.team16.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

data class Tab(val label: String, val icon: ImageVector, val route: Any)

@Composable
fun BottomBar(navController: NavController, tabs: List<Tab>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentTab = run {
        val currentDestination = navBackStackEntry?.destination ?: return@run null
        tabs.find { tab -> currentDestination.hierarchy.any { it.hasRoute(tab.route::class) } }
    }
    
    NavigationBar {
        tabs.forEach {
            NavigationBarItem(
                icon = { Icon(it.icon, it.label) },
                label = { Text(it.label) },
                selected = it.route == currentTab?.route,
                onClick = {
                    navController.navigate(it.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
