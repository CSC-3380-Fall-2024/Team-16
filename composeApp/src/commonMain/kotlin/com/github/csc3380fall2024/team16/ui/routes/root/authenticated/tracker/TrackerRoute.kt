package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.tracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.csc3380fall2024.team16.AppResources
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import kotlinx.serialization.Serializable

@Serializable
object TrackerRoute

@Composable
fun TrackerRoute.compose(app: AppResources, token: String) {
    val foodLogs by app.foodLogsRepo.updates.collectAsState()
    val viewModel = viewModel { TrackerViewModel(app.foodLogsRepo, token) }
    LaunchedEffect(Unit) {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        viewModel.fetch(
            today,
            today.minus(1, DateTimeUnit.DAY),
            today.minus(2, DateTimeUnit.DAY),
            today.minus(3, DateTimeUnit.DAY),
        )
    }
    foodLogs?.let {
        TrackerScreen(
            foodLogs = it,
            onAddFoodLog = viewModel::addFoodLog,
            onRemoveFoodLog = viewModel::removeFoodLog,
            onSetCalorieGoal = viewModel::setCalorieGoal,
            error = viewModel.error,
        )
    }
}
