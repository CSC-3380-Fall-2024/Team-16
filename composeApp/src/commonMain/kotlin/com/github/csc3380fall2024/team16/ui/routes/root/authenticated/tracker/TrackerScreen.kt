package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.csc3380fall2024.team16.repository.FoodLogs
import com.github.csc3380fall2024.team16.ui.components.ErrWrap
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

@Composable
fun TrackerScreen(
    foodLogs: FoodLogs,
    onAddFoodLog: (LocalDate, String, Int) -> Unit,
    onRemoveFoodLog: (LocalDate, Int) -> Unit,
    onSetCalorieGoal: (Int) -> Unit,
    error: Boolean,
) {
    var showEditCalorieDialog by remember { mutableStateOf(false) }
    var showAddFoodDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    
    var selectedDate by remember { mutableStateOf(Clock.System.todayIn(TimeZone.currentSystemDefault())) }
    val selectedFoodLogs by derivedStateOf { foodLogs.logs[selectedDate] ?: emptyList() }
    
    ErrWrap(if (error) "There was an error fetching data." else null) {
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
                currentCalories = selectedFoodLogs.sumOf { it.calories },
                calorieGoal = foodLogs.calorieGoal
            )
            
            Text(
                text = "Set Calorie Goal",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .clickable { showEditCalorieDialog = true }
                    .padding(horizontal = 20.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { showDateDialog = true },
                    modifier = Modifier.weight(1f)
                ) { Text(text = "View Previous") }
                
                Button(
                    onClick = { showAddFoodDialog = true },
                    modifier = Modifier.weight(1f)
                ) { Text(text = "Add Food") }
            }
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedFoodLogs.isEmpty()) {
                    item {
                        Text(
                            text = "No foods added yet.",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    items(selectedFoodLogs) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceBright)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "${it.food}: ${it.calories} kcal",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "X",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable { onRemoveFoodLog(selectedDate, it.id) },
                            )
                        }
                    }
                }
            }
        }
    }
    
    if (showAddFoodDialog) {
        AddFoodDialog(
            onClose = { showAddFoodDialog = false },
            onSave = { newFoodName, calories ->
                onAddFoodLog(selectedDate, newFoodName, calories)
            }
        )
    }
    
    if (showEditCalorieDialog) {
        EditCalorieDialog(
            initialValue = foodLogs.calorieGoal,
            onClose = { showEditCalorieDialog = false },
            onUpdateCalories = { onSetCalorieGoal(it) },
        )
    }
    if (showDateDialog) {
        DateNavAlert(
            selectedDate = selectedDate,
            onDateChanged = { newDate -> selectedDate = newDate },
            onDismiss = { showDateDialog = false }
        )
    }
}

@Composable
fun EditCalorieDialog(
    initialValue: Int,
    onClose: () -> Unit,
    onUpdateCalories: (Int) -> Unit
) {
    var goalStr by remember { mutableStateOf(initialValue.toString()) }
    val goal by derivedStateOf { goalStr.toIntOrNull()?.takeIf { it > 0 } }
    
    AlertDialog(
        title = { Text(text = "Set Calorie Goal") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TextField(
                    value = goalStr,
                    onValueChange = { goalStr = it },
                    label = { Text("Calorie Goal") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        onDismissRequest = { onClose() },
        dismissButton = { TextButton({ onClose() }) { Text("Dismiss") } },
        confirmButton = {
            TextButton(
                enabled = goal != null,
                onClick = {
                    onUpdateCalories(goal!!)
                    onClose()
                }
            ) { Text("Confirm") }
        },
    )
}

@Composable
fun AddFoodDialog(onClose: () -> Unit, onSave: (String, Int) -> Unit) {
    var foodName by remember { mutableStateOf("") }
    var caloriesStr by remember { mutableStateOf("") }
    val calories by derivedStateOf { caloriesStr.toIntOrNull()?.takeIf { it >= 0 } }
    
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(
                enabled = foodName.isNotBlank() && calories != null,
                onClick = {
                    onSave(foodName.trim(), calories!!)
                    onClose()
                }
            ) { Text("Save") }
        },
        dismissButton = { Button(onClose) { Text("Cancel") } },
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
                    value = caloriesStr,
                    onValueChange = { caloriesStr = it },
                    label = { Text("Calories") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
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
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
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
            modifier = Modifier.height(20.dp).fillMaxWidth().padding(vertical = 4.dp),
            color = progressColor,
            strokeCap = StrokeCap.Square,
            gapSize = 0.dp,
            drawStopIndicator = {},
        )
        Text(
            text = "$currentCalories / $calorieGoal kcal",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun DateNavAlert(
    selectedDate: LocalDate,
    onDateChanged: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    var currentDate by remember { mutableStateOf(selectedDate) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select Date") },
        text = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        currentDate -= DatePeriod(days = 1)
                        onDateChanged(currentDate)
                    }
                ) { Text(text = "<") }
                
                Text(
                    text = currentDate.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Button(
                    onClick = {
                        currentDate += DatePeriod(days = 1)
                        onDateChanged(currentDate)
                    }
                ) { Text(text = ">") }
            }
        },
        confirmButton = { TextButton(onDismiss) { Text("Done") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

