package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutGenerator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WorkoutLoggerScreen(onBack: () -> Unit) {
    // Mutable list to track all workouts, initialized with workouts from SavedWorkouts
    val allWorkouts = remember { mutableStateListOf<String>().apply { addAll(SavedWorkouts.getGeneratedWorkouts()) } }
    
    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { onBack() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Workout Log:", fontSize = 18.sp)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Display all workouts
        if (allWorkouts.isNotEmpty()) {
            for (workout in allWorkouts) {
                Text(text = workout, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            Text("No workouts have been generated yet.", fontSize = 14.sp)
        }
    }
}
