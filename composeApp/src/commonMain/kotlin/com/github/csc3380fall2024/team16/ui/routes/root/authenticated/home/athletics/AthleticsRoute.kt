package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.athletics

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object AthleticsRoute

@Composable
fun AthleticsRoute.compose(onBack: () -> Unit) {
    AthleticsScreen(onBack)
}