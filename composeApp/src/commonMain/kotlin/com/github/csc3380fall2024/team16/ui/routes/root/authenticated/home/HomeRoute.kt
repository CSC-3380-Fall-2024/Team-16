package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home

import androidx.compose.runtime.Composable
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
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Composable
fun HomeRoute.compose() {
    Navigator(start = ChooseRoute) {
        route<ChooseRoute> { this.compose(navController) }
        route<WorkoutGeneratorRoute> { this.compose(navController) }
        route<BodybuildingRoute> { this.compose(navController) }
        route<PowerliftingRoute> { this.compose(navController) }
        route<AthleticsRoute> { this.compose(navController) }
        route<WeightLossRoute> { this.compose(navController) }
    }
}
