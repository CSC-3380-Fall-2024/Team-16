package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Tracker

@Composable
fun TrackerPage(navController: NavController, currentCalories: Float, calorieGoal: Float) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState), // Enable vertical scrolling
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceBright)
                    .padding(vertical = 60.dp)
            ) {
                Text(
                    "Tracker",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            // Calorie Progress Section
            CalorieProgress(currentCalories = currentCalories, calorieGoal = calorieGoal)
            
            // Add Food Button
            Button(
                onClick = { /* Navigate to food search page */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(text = "Add Food")
            }
            
            // Food List Placeholder Section
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f) // Simulating a large content area
                        .background(color = MaterialTheme.colorScheme.surfaceBright),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Food list goes here",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            
            // Back to Top Button
            Button(
                onClick = {
                    // Scroll to the top when button is clicked
                    coroutineScope.launch {
                        scrollState.animateScrollTo(0)
                    }
                },
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Back to Top")
            }
        }
    }
}

@Composable
fun CalorieProgress(currentCalories: Float, calorieGoal: Float) {
    val progress = (currentCalories / calorieGoal).coerceIn(0f, 1f)
    
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Calorie Progress",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        LinearProgressIndicator(
            progress = { progress }, // Wrap progress in a lambda
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        )
        Text(
            text = "${currentCalories.toInt()} / ${calorieGoal.toInt()} kcal",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
