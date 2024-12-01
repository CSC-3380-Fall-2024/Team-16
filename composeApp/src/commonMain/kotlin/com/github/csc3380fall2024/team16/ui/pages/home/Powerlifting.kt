package com.github.csc3380fall2024.team16.ui.pages.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.ExerciseRepository
import com.github.csc3380fall2024.team16.ui.components.ExerciseItem

@Composable
fun PowerliftingPage(navController: NavController) {
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
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            Text("Powerlifting Exercises", fontSize = 24.sp, modifier = Modifier.padding(8.dp))
            
            // Fetch exercises list from repository
            val exercises = ExerciseRepository.getPowerliftingExercises()
            
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Populate LazyColumn with Exercise items
                items(exercises) { exercise ->
                    ExerciseItem(exercise)
                }
            }
        }
    }
}
