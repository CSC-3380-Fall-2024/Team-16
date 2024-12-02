package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.home.bodybuilding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.csc3380fall2024.team16.ExerciseRepository
import com.github.csc3380fall2024.team16.ui.components.ExerciseItem

@Composable
fun BodybuildingScreen(onBack: () -> Unit) {
    
    var searchQuery by remember { mutableStateOf("") }
    val exercises = ExerciseRepository.getBodybuildingExercises()
    val filteredExercises = exercises.filter { exercise ->
        exercise.name.contains(searchQuery, ignoreCase = true)
    }
    
    Box(Modifier.fillMaxSize()) {
        // Back Button in the top-left corner
        Button(
            onClick = { onBack() },
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
            Text("Bodybuilding Exercises", fontSize = 24.sp, modifier = Modifier.padding(8.dp))
            
            TextField(
                value = searchQuery,
                singleLine = true,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Search exercises...") }
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Populate LazyColumn with Exercise items
                items(filteredExercises) { exercise ->
                    ExerciseItem(exercise)
                }
            }
        }
    }
}
