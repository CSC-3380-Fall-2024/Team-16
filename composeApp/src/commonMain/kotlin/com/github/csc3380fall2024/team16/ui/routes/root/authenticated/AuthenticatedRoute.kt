package com.github.csc3380fall2024.team16.ui.routes.root.authenticated

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.csc3380fall2024.team16.AppResources
import com.github.csc3380fall2024.team16.Navigator
import com.github.csc3380fall2024.team16.ui.components.BottomBar
import com.github.csc3380fall2024.team16.ui.components.Tab
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.HomeRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.news.NewsRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.news.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social.SocialRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.tracker.TrackerRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.tracker.compose
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticatedRoute(val token: String, val username: String)

@Composable
fun AuthenticatedRoute.compose(app: AppResources) {
    Navigator(
        start = HomeRoute,
        compose = {
            val tabs = listOf(
                Tab("Home", Icons.Filled.Home, HomeRoute),
                Tab("Tracker", Icons.Filled.InsertChart, TrackerRoute),
                Tab("News", Icons.Filled.Newspaper, NewsRoute(token)),
                Tab("Social", Icons.Filled.Group, SocialRoute),
            )
            Scaffold(
                bottomBar = { BottomBar(navController, tabs) }
            ) {
                Box(Modifier.padding(it)) { it() }
            }
        }
    ) {
        route<HomeRoute> { this.compose(app, username, token) }
        route<TrackerRoute> { this.compose(app, token) }
        route<NewsRoute> { this.compose(app) }
        route<SocialRoute> { this.compose(app, token) }
    }
}
