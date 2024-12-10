package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.workoutLogger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.csc3380fall2024.team16.WorkoutLog

@Composable
fun WorkoutLoggerScreen(
    foodLogs: List<WorkoutLog>,
    onBack: () -> Unit
) {
    val scroll = rememberScrollState()

    Column(modifier = Modifier.padding(16.dp).verticalScroll(scroll)) {
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
        if (foodLogs.isNotEmpty()) {
            for (workout in foodLogs) {
                Text(text = workout.log, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            Text("No workouts have been generated yet.", fontSize = 14.sp)
        }
    }
}
