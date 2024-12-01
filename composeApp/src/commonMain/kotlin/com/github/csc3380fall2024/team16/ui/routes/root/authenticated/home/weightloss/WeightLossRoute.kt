package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.weightloss

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object WeightLossRoute

@Composable
fun WeightLossRoute.compose(onBack: () -> Unit) {
    WeightLossScreen(onBack)
}
