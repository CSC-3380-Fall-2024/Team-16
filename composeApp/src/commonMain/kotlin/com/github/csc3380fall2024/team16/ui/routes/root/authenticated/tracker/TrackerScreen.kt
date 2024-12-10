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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import kotlin.math.absoluteValue
import kotlin.math.pow

@Composable
fun TrackerScreen(
    foodLogs: FoodLogs,
    onAddFoodLog: (LocalDate, String, Int, Int, Int, Int) -> Unit,
    onRemoveFoodLog: (LocalDate, Int) -> Unit,
    onSetGoals: (Int, Int, Int, Int) -> Unit,
    error: Boolean,
) {
    var showEditGoalDialog by remember { mutableStateOf(false) }
    var showAddFoodDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    
    var selectedDate by remember { mutableStateOf(Clock.System.todayIn(TimeZone.currentSystemDefault())) }
    val selectedFoodLogs = foodLogs.logs[selectedDate] ?: emptyList()
    
    val state = rememberScrollState()
    
    ErrWrap(if (error) "There was an error fetching data." else null) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(state),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            CalorieProgress(
                currentCalories = selectedFoodLogs.sumOf { it.calories },
                calorieGoal = foodLogs.calorieGoal,
                protein = selectedFoodLogs.sumOf { it.proteinGrams },
                fat = selectedFoodLogs.sumOf { it.fatsGrams },
                carbs = selectedFoodLogs.sumOf { it.carbsGrams },
                proteinGoal = foodLogs.proteinGoal,
                fatGoal = foodLogs.fatGoal,
                carbsGoal = foodLogs.carbsGoal
            )
            
            Text(
                text = "Set Goals",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .clickable { showEditGoalDialog = true }
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
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedFoodLogs.isEmpty()) {
                    Text(
                        text = "No foods added yet.",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    selectedFoodLogs.forEach {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceBright)
                                .padding(8.dp)
                        ) {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = it.food,
                                    modifier = Modifier.weight(1f).align(Alignment.Top),
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Start,
                                )
                                Text(
                                    text = "calories: ${it.calories}",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = "X",
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { onRemoveFoodLog(selectedDate, it.id) },
                                    textAlign = TextAlign.End,
                                )
                            }
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = "protein: ${it.proteinGrams}g",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                )
                                Text(
                                    text = "fats: ${it.fatsGrams}g",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = "carbs: ${it.carbsGrams}g",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    if (showAddFoodDialog) {
        AddFoodDialog(
            onClose = { showAddFoodDialog = false },
            onSave = { newFoodName, calories, protein, fat, carbs ->
                onAddFoodLog(selectedDate, newFoodName, calories, protein, fat, carbs)
            }
        )
    }
    
    if (showEditGoalDialog) {
        EditGoalsDialog(
            initialValue = foodLogs.calorieGoal,
            initialProtein = foodLogs.proteinGoal,
            initialFat = foodLogs.fatGoal,
            initialCarbs = foodLogs.carbsGoal,
            onClose = { showEditGoalDialog = false },
            onUpdateGoals = { calorieGoal, proteinGoal, fatGoal, carbsGoal ->
                onSetGoals(calorieGoal, proteinGoal, fatGoal, carbsGoal)
            }
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
fun EditGoalsDialog(
    initialValue: Int,
    initialProtein: Int,
    initialFat: Int,
    initialCarbs: Int,
    onClose: () -> Unit,
    onUpdateGoals: (Int, Int, Int, Int) -> Unit
) {
    var goalStr by remember { mutableStateOf(initialValue.toString()) }
    var goalProteinStr by remember { mutableStateOf(initialProtein.toString()) }
    var goalFatStr by remember { mutableStateOf(initialFat.toString()) }
    var goalCarbsStr by remember { mutableStateOf(initialCarbs.toString()) }
    
    val goalCalories = goalStr.toIntOrNull()?.takeIf { it >= 0 }
    val goalProtein = goalProteinStr.toIntOrNull()?.takeIf { it >= 0 }
    val goalFat = goalFatStr.toIntOrNull()?.takeIf { it >= 0 }
    val goalCarbs = goalCarbsStr.toIntOrNull()?.takeIf { it >= 0 }
    
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
                TextField(
                    value = goalProteinStr,
                    onValueChange = { goalProteinStr = it },
                    label = { Text("Protein Goal (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = goalFatStr,
                    onValueChange = { goalFatStr = it },
                    label = { Text("Fat Goal (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = goalCarbsStr,
                    onValueChange = { goalCarbsStr = it },
                    label = { Text("Carbs Goal (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        onDismissRequest = { onClose() },
        dismissButton = { TextButton({ onClose() }) { Text("Dismiss") } },
        confirmButton = {
            TextButton(
                enabled = goalCalories != null && goalProtein != null && goalFat != null && goalCarbs != null,
                onClick = {
                    onUpdateGoals(goalCalories!!, goalProtein!!, goalFat!!, goalCarbs!!)
                    onClose()
                }
            ) { Text("Confirm") }
        },
    )
}

@Composable
fun AddFoodDialog(onClose: () -> Unit, onSave: (String, Int, Int, Int, Int) -> Unit) {
    var foodName by remember { mutableStateOf("") }
    var caloriesStr by remember { mutableStateOf("") }
    var proteinStr by remember { mutableStateOf("") }
    var fatStr by remember { mutableStateOf("") }
    var carbsStr by remember { mutableStateOf("") }
    
    val calories = caloriesStr.toIntOrNull()?.takeIf { it >= 0 }
    val protein = proteinStr.toIntOrNull()?.takeIf { it >= 0 }
    val fat = fatStr.toIntOrNull()?.takeIf { it >= 0 }
    val carbs = carbsStr.toIntOrNull()?.takeIf { it >= 0 }
    
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(
                enabled = foodName.isNotBlank() && calories != null && protein != null && fat != null && carbs != null,
                onClick = {
                    onSave(foodName.trim(), calories!!, protein!!, fat!!, carbs!!)
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
                TextField(
                    value = proteinStr,
                    onValueChange = { proteinStr = it },
                    label = { Text("Protein (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = fatStr,
                    onValueChange = { fatStr = it },
                    label = { Text("Fat (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = carbsStr,
                    onValueChange = { carbsStr = it },
                    label = { Text("Carbs (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Composable
fun CalorieProgress(currentCalories: Int, calorieGoal: Int, protein: Int, fat: Int, carbs: Int, proteinGoal: Int, fatGoal: Int, carbsGoal: Int) {
    val progress = currentCalories.toFloat() / calorieGoal
    val remaining = calorieGoal - currentCalories
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
        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = { progress },
                color = interpolateColor(progress),
                strokeWidth = 12.dp,
                gapSize = 0.dp,
                strokeCap = StrokeCap.Square,
            )
            
            Column {
                Text(
                    "${remaining.absoluteValue}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Text(
                    text = "calories " + if (remaining >= 0) "left" else "over",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                )
            }
            
        }
        Text(
            text = "$currentCalories / $calorieGoal calories",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MacroProgress("Protein", protein, proteinGoal, "g")
            MacroProgress("Fat", fat, fatGoal, "g")
            MacroProgress("Carbs", carbs, carbsGoal, "g")
        }
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

@Composable
fun MacroProgress(label: String, current: Int, goal: Int, unit: String) {
    val progress = current.toFloat() / goal
    
    Column(
        modifier = Modifier.width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = interpolateColor(progress),
            gapSize = 0.dp,
            drawStopIndicator = {},
            strokeCap = StrokeCap.Square,
        )
        Text(
            text = "$current / $goal $unit",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

// quadratic color interpolation between red and green, accounting for going over
fun interpolateColor(progress: Float) = lerp(
    start = Color.Red,
    stop = Color.Green,
    fraction = 1 - (1 - progress.pow(2)).absoluteValue,
)
