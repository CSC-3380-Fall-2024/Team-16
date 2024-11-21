package com.github.csc3380fall2024.team16.ui.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object WorkoutGenerator

@Composable
fun WorkoutGeneratorPage(navController: NavController) {
    // State management
    var selectedGoal by remember { mutableStateOf<String?>(null) }
    var selectedTarget by remember { mutableStateOf<String?>(null) }
    var selectedIntensity by remember { mutableStateOf<String?>(null) }
    
    val goalOptions = listOf("Strength", "Aesthetics", "Sports Performance", "Weight Loss")
    val targetOptions = mapOf(
        "Strength" to listOf("Squat", "Bench", "Deadlift"),
        "Aesthetics" to listOf("Arms", "Abs", "Back", "Legs"),
        "Sports Performance" to listOf("Vertical", "Speed", "Endurance"),
        "Weight Loss" to listOf("Cardio", "HIIT", "Strength Training")
    )
    val intensityOptions = listOf("Low", "Moderate", "High")
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(Modifier.fillMaxSize()) {
            // Back Button in the top-left corner
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 50.dp, start = 8.dp)
            ) {
                Text("Back")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 110.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Workout Generator", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                
                // Step 1: Goal Selection
                if (selectedGoal == null) {
                    Text("Select a Goal:")
                    goalOptions.forEach { goal ->
                        Button(
                            onClick = { selectedGoal = goal },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(goal)
                        }
                    }
                }
                
                // Step 2: Target Selection
                selectedGoal?.let { goal ->
                    if (selectedTarget == null) {
                        Text("Select a Target for $goal:")
                        targetOptions[goal]?.forEach { target ->
                            Button(
                                onClick = { selectedTarget = target },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(target)
                            }
                        }
                    }
                }
                
                // Step 3: Intensity Selection
                selectedTarget?.let { target ->
                    if (selectedIntensity == null) {
                        Text("Select Intensity for $target:")
                        intensityOptions.forEach { intensity ->
                            Button(
                                onClick = { selectedIntensity = intensity },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(intensity)
                            }
                        }
                    }
                }
                
                // Final Step: Show Summary or Generate Workout
                if (selectedGoal != null && selectedTarget != null && selectedIntensity != null) {
                    Text(
                        "Goal: $selectedGoal\nTarget: $selectedTarget\nIntensity: $selectedIntensity",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Button(
                        onClick = { /* Logic to generate workout */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Generate Workout")
                    }
                }
            }
        }
    }
}
