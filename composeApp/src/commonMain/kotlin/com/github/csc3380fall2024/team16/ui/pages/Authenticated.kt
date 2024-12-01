package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.github.csc3380fall2024.team16.Navigator
import com.github.csc3380fall2024.team16.RpcClient
import com.github.csc3380fall2024.team16.ui.components.BottomBar
import com.github.csc3380fall2024.team16.ui.components.Tab
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
import com.github.csc3380fall2024.team16.ui.pages.home.WorkoutGenerator
import com.github.csc3380fall2024.team16.ui.pages.home.WorkoutGeneratorPage
import kotlinx.serialization.Serializable

@Serializable
data class Authenticated(val token: String)

@Composable
fun AuthenticatedRoute(client: RpcClient, token: String) {
	Navigator(
		start = Home,
		compose = {
			val tabs = listOf(
				Tab("Home", Icons.Filled.Home, Home),
				Tab("Tracker", Icons.Filled.InsertChart, Tracker),
				Tab("News", Icons.Filled.Newspaper, News),
				Tab("Social", Icons.Filled.Group, Social),
			)
			Scaffold(bottomBar = { BottomBar(navController, tabs) }) { it() }
		}
	) {
		route<Home> {
			Navigator(start = Choose) {
				route<Choose> { ChoosePage(navController) }
				route<WorkoutGenerator> { WorkoutGeneratorPage(navController) }
				route<Bodybuilding> { BodybuildingPage(navController) }
				route<Powerlifting> { PowerliftingPage(navController) }
				route<Athletics> { AthleticsPage(navController) }
				route<WeightLoss> { WeightLossPage(navController) }
			}
		}
		route<Tracker> { TrackerPage(navController, currentCalories = 0, calorieGoal = 2000) }
		route<News> { NewsPage(navController) }
		route<Social> { SocialPage(navController) }
		route<Add> { AddFood(navController) }
	}
}
