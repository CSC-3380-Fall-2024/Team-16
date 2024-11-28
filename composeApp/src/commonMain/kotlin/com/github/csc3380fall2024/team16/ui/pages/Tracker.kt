package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.layout.Column
import kotlinx.serialization.Serializable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Serializable
object Tracker

@Composable
fun TrackerPage(navController: NavController, currentCalories: Float, calorieGoal: Float) {
    var showDialogState by remember { mutableStateOf(false) }
    var updatedCurrentCaloriesState by remember { mutableStateOf(currentCalories) }
    var updatedCalorieGoalState by remember { mutableStateOf(calorieGoal) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 60.dp)
            ) {
                Text(
                    text = "Tracker",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            CalorieProgress(
                currentCalories = updatedCurrentCaloriesState,
                calorieGoal = updatedCalorieGoalState
            )
            
            Text(
                text = "Modify Calorie Data",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .clickable { showDialogState = true }
                    .padding(horizontal = 20.dp)
            )
            
            Button(
                onClick = { /* Navigate to food search page */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(text = "Add Food")
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Food items will be listed here",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
    
    if (showDialogState) {
        EditCalorie(
            onDismissRequest = { showDialogState = false },
            onConfirmation = {
                showDialogState = false
            },
            dialogTitle = "Modify Calorie Data",
            dialogText = "Please update your current calories and goal.",
            icon = Icons.Default.Edit,
            updatedCurrentCaloriesState = updatedCurrentCaloriesState,
            updatedCalorieGoalState = updatedCalorieGoalState,
            onUpdateCalories = { newCurrentCalories, newGoal ->
                updatedCurrentCaloriesState = newCurrentCalories
                updatedCalorieGoalState = newGoal
            }
        )
    }
}

@Composable
fun EditCalorie(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    updatedCurrentCaloriesState: Float,
    updatedCalorieGoalState: Float,
    onUpdateCalories: (Float, Float) -> Unit
) {
    var currentCaloriesText by remember { mutableStateOf(updatedCurrentCaloriesState.toString()) }
    var calorieGoalText by remember { mutableStateOf(updatedCalorieGoalState.toString()) }
    
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TextField(
                    value = currentCaloriesText,
                    onValueChange = { currentCaloriesText = it },
                    label = { Text("Current Calories") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = calorieGoalText,
                    onValueChange = { calorieGoalText = it },
                    label = { Text("Calorie Goal") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newCurrentCalories by lazy { currentCaloriesText.toFloatOrNull() ?: updatedCurrentCaloriesState }
                    val newGoal by lazy { calorieGoalText.toFloatOrNull() ?: updatedCalorieGoalState }
                    onUpdateCalories(newCurrentCalories, newGoal)
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
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
            progress = { progress },
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
