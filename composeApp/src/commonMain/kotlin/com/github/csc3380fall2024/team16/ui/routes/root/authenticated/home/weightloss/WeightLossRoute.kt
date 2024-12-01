package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.weightloss

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.ui.pages.home.WeightLossPage
import kotlinx.serialization.Serializable

@Serializable
object WeightLossRoute

@Composable
fun WeightLossRoute.compose(navController: NavController) {
    WeightLossPage(navController)
}
