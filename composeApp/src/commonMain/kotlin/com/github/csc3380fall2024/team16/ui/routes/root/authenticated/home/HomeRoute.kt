package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home

import androidx.compose.runtime.Composable
import com.github.csc3380fall2024.team16.AppResources
import com.github.csc3380fall2024.team16.Navigator
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.athletics.AthleticsRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.athletics.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.bodybuilding.BodybuildingRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.bodybuilding.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.choose.ChooseRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.choose.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.powerlifting.PowerliftingRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.powerlifting.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.weightloss.WeightLossRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.weightloss.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutGenerator.WorkoutGeneratorRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutGenerator.compose
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutLogger.WorkoutLoggerRoute
import com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutLogger.compose
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Composable
fun HomeRoute.compose(app: AppResources, username: String, token: String) {
    Navigator(start = ChooseRoute) {
        route<ChooseRoute> {
            this.compose(
                username = username,
                onNavigateWorkoutGenerator = { navController.navigate(WorkoutGeneratorRoute) },
                onNavigateBodyBuilding = { navController.navigate(BodybuildingRoute) },
                onNavigatePowerlifting = { navController.navigate(PowerliftingRoute) },
                onNavigateAthletics = { navController.navigate(AthleticsRoute) },
                onNavigateWeightloss = { navController.navigate(WeightLossRoute) },
                onNavigateWorkoutLogger = { navController.navigate(WorkoutLoggerRoute) }
            )
        }
        route<WorkoutGeneratorRoute> { this.compose(app, token, onBack = { navController.popBackStack() }) }
        route<BodybuildingRoute> { this.compose(onBack = { navController.popBackStack() }) }
        route<PowerliftingRoute> { this.compose(onBack = { navController.popBackStack() }) }
        route<AthleticsRoute> { this.compose(onBack = { navController.popBackStack() }) }
        route<WeightLossRoute> { this.compose(onBack = { navController.popBackStack() }) }
        route<WorkoutLoggerRoute> { this.compose(app, token, onBack = { navController.popBackStack() }) }
    }
}
