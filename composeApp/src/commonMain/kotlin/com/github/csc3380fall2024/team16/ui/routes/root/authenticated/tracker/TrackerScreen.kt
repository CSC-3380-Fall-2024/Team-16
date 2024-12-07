package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrackerScreen(currentCalories: Int, calorieGoal: Int) {
    var showEditCalorieDialog by remember { mutableStateOf(false) }
    var updatedCurrentCalories by remember { mutableStateOf(currentCalories) }
    var updatedCalorieGoal by remember { mutableStateOf(calorieGoal) }
    var showAddFoodDialog by remember { mutableStateOf(false) }
    var foodList by remember { mutableStateOf(listOf<Pair<String, Int>>()) }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceBright)
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = "Tracker",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        
        CalorieProgress(
            currentCalories = updatedCurrentCalories,
            calorieGoal = updatedCalorieGoal
        )
        
        Text(
            text = "Modify Calorie Data",
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .clickable { showEditCalorieDialog = true }
                .padding(horizontal = 20.dp)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { /* Should display previous days */ },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "View Previous")
            }
            
            Button(
                onClick = { showAddFoodDialog = true },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Add Food")
            }
        }
        
        
        if (showAddFoodDialog) {
            AddFoodDialog(
                onDismiss = { showAddFoodDialog = false },
                onSave = { newFoodName, calories ->
                    foodList += newFoodName to calories
                    updatedCurrentCalories += calories
                    showAddFoodDialog = false
                }
            )
        }
        
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (foodList.isEmpty()) {
                Text(
                    text = "No foods added yet.",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                foodList.forEach { (food, calories) ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceBright)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "$food: $calories kcal",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
    
    if (showEditCalorieDialog) {
        EditCalorieDialog(
            onDismissRequest = { showEditCalorieDialog = false },
            onConfirmation = {
                showEditCalorieDialog = false
            },
            dialogTitle = "Modify Calorie Data",
            dialogText = "Please update your current calories and goal.",
            icon = Icons.Default.Edit,
            updatedCurrentCaloriesState = updatedCurrentCalories,
            updatedCalorieGoalState = updatedCalorieGoal,
            onUpdateCalories = { newCurrentCalories, newGoal ->
                updatedCurrentCalories = newCurrentCalories
                updatedCalorieGoal = newGoal
            }
        )
    }
}

@Composable
fun EditCalorieDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    updatedCurrentCaloriesState: Int,
    updatedCalorieGoalState: Int,
    onUpdateCalories: (Int, Int) -> Unit
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
                    val newCurrentCalories = currentCaloriesText.toIntOrNull() ?: updatedCurrentCaloriesState
                    val newGoal = calorieGoalText.toIntOrNull() ?: updatedCalorieGoalState
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
fun AddFoodDialog(onDismiss: () -> Unit, onSave: (String, Int) -> Unit) {
    var foodName by remember { mutableStateOf("") }
    var calorieCount by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    val calories = calorieCount.toFloatOrNull()
                    if (foodName.isNotBlank() && calories != null) {
                        if (calories >= 0) {
                            onSave(foodName.trim(), calories.toInt())
                            foodName = ""
                            calorieCount = ""
                            errorMessage = ""
                        } else {
                            errorMessage = "Calories cannot be negative."
                        }
                    } else {
                        errorMessage = "Invalid input. Please enter valid food and calories."
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        },
        title = { Text("Add Food") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Food Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = calorieCount,
                    onValueChange = { calorieCount = it },
                    label = { Text("Calories") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun CalorieProgress(currentCalories: Int, calorieGoal: Int) {
    val progress = currentCalories.toFloat() / calorieGoal
    val progressPercent = progress * 100
    val progressColor = when {
        progressPercent < 55 -> MaterialTheme.colorScheme.errorContainer
        progressPercent < 95 -> MaterialTheme.colorScheme.error
        progressPercent <= 100 -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.errorContainer
    }
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
            color = progressColor,
        )
        Text(
            text = "$currentCalories / $calorieGoal kcal",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
