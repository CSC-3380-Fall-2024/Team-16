package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.tracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.repository.FoodLogsRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TrackerViewModel(private val foodLogsRepo: FoodLogsRepository, private val token: String) :
    ViewModel() {
    var error by mutableStateOf(false)
        private set

    fun fetch(vararg dates: LocalDate) = viewModelScope.launch {
        try {
            foodLogsRepo.fetch(token, *dates)
            error = false
        } catch (e: Exception) {
            error = true
        }
    }

    fun setGoals(calorieGoal: Int, proteinGoal: Int, fatGoal: Int, carbsGoal: Int) =
        viewModelScope.launch {
            foodLogsRepo.setGoals(calorieGoal, proteinGoal, fatGoal, carbsGoal)
        }

    fun addFoodLog(
        date: LocalDate,
        food: String,
        calories: Int,
        protein: Int,
        fat: Int,
        carbs: Int
    ) = viewModelScope.launch {
        try {
            foodLogsRepo.logFood(token, date, food, calories, protein, fat, carbs)
            error = false
        } catch (e: Exception) {
            error = true
        }
    }

    fun removeFoodLog(date: LocalDate, id: Int) = viewModelScope.launch {
        try {
            foodLogsRepo.removeFoodLog(token, date, id)
            error = false
        } catch (e: Exception) {
            error = true
        }
    }
}
