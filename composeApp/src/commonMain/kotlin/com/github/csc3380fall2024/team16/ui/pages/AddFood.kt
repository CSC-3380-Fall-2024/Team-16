package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object Add

@Composable
fun AddFood(navController: NavController) {
    var foodName by remember { mutableStateOf("") }
    var servingName by remember { mutableStateOf("") }
    var servingWeight by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var isServingNameEmpty by remember { mutableStateOf(false) }
    var isFoodNameEmpty by remember { mutableStateOf(false) }
    var isServingWeightEmpty by remember { mutableStateOf(false) }
    var isCaloriesEmpty by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(top = 40.dp, bottom = 10.dp)
        ) {
            Text(
                "Add New Food",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextField(
                value = foodName,
                onValueChange = {
                    foodName = it
                    isFoodNameEmpty = false
                },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Food Name") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            if (isFoodNameEmpty) {
                Text(
                    "*Required",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            TextField(
                value = servingName,
                onValueChange = {
                    servingName = it
                    isServingNameEmpty = false
                },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Serving Name") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            if (isServingNameEmpty) {
                Text(
                    "*Required",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            TextField(
                value = servingWeight,
                onValueChange = {
                    servingWeight = it
                    isServingWeightEmpty = false
                },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Serving Weight") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            if (isServingWeightEmpty) {
                Text(
                    "*Required",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            TextField(
                value = calories,
                onValueChange = {
                    calories = it
                    isCaloriesEmpty = false
                },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Calories") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            if (isCaloriesEmpty) {
                Text(
                    "*Required",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            TextField(
                value = fat,
                onValueChange = { fat = it },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Total Fat") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            
            TextField(
                value = carbs,
                onValueChange = { carbs = it },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Total Carbs") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            
            TextField(
                value = protein,
                onValueChange = { protein = it },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Total Protein") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            
            Button(
                onClick = {
                    isFoodNameEmpty = foodName.isBlank()
                    isServingNameEmpty = servingName.isBlank()
                    isServingWeightEmpty = servingWeight.isBlank()
                    isCaloriesEmpty = calories.isBlank()
                    
                    if (isFoodNameEmpty || isServingNameEmpty || isServingWeightEmpty || isCaloriesEmpty) {
                        errorMessage = "Please fill in all required fields."
                    } else {
                        errorMessage = ""
                        // database save logic here
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(text = "Save Food")
            }
        }
    }
}
