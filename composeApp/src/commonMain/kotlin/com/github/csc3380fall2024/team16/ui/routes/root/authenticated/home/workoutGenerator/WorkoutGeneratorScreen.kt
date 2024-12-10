package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutGenerator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.github.csc3380fall2024.team16.Exercise
import com.github.csc3380fall2024.team16.ExerciseRepository
import com.github.csc3380fall2024.team16.ui.components.ErrWrap

@Composable
fun WorkoutGeneratorScreen(
    onLogWorkout: (goal: String, target: String, intensity: String) -> Unit,
    onBack: () -> Unit,
    error: Boolean,
) {
    var selectedGoal by remember { mutableStateOf<String?>(null) }
    var selectedTarget by remember { mutableStateOf<String?>(null) }
    var selectedIntensity by remember { mutableStateOf<String?>(null) }
    var generatedWorkout by remember { mutableStateOf(emptyList<Exercise>()) }
    
    val goalOptions = listOf("Strength", "Aesthetics", "Sports Performance", "Weight Loss")
    val targetOptions = mapOf(
        "Strength" to listOf("Squat", "Bench", "Deadlift"),
        "Aesthetics" to listOf("Arms", "Abs", "Back", "Legs", "Chest"),
        "Sports Performance" to listOf("Vertical", "Speed", "Endurance"),
        "Weight Loss" to listOf("Cardio", "HIIT", "Strength Training")
    )
    val intensityOptions = listOf("Low", "Moderate", "High")
    
    ErrWrap(if (error) "There was an error logging this workout." else null) {
        Box(Modifier.fillMaxSize()) {
            Button(
                onClick = { onBack() },
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
                        onClick = {
                            if (generatedWorkout.isNotEmpty()) return@Button
                            
                            val exercises = when (selectedGoal) {
                                "Strength"           -> ExerciseRepository.getPowerliftingExercises()
                                "Aesthetics"         -> ExerciseRepository.getBodybuildingExercises()
                                "Sports Performance" -> ExerciseRepository.getAthleticExercises()
                                "Weight Loss"        -> ExerciseRepository.getWeightLossExercises()
                                else                 -> emptyList()
                            }
                            
                            val filteredExercises = exercises.filter {
                                it.target.contains(selectedTarget!!, ignoreCase = true)
                            }
                            
                            val (sets, reps) = when (selectedIntensity) {
                                "Low"      -> 3 to 6
                                "Moderate" -> 4 to 8
                                "High"     -> 4 to 12
                                else       -> 3 to 6
                            }
                            
                            val randomExercises = filteredExercises.shuffled().take(6)
                            
                            generatedWorkout = randomExercises.map { exercise ->
                                exercise.copy(description = "${exercise.description} - $sets sets of $reps reps")
                            }
                            
                            val goal = selectedGoal
                            val target = selectedTarget
                            val intensity = selectedIntensity
                            
                            if (goal != null && target != null && intensity != null) {
                                onLogWorkout(goal, target, intensity)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Generate Workout")
                    }
                    
                    if (generatedWorkout.isNotEmpty()) {
                        Column(modifier = Modifier.padding(top = 16.dp)) {
                            Text("Generated Workout:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            generatedWorkout.forEach { exercise ->
                                Text("- ${exercise.name}: ${exercise.description}", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
