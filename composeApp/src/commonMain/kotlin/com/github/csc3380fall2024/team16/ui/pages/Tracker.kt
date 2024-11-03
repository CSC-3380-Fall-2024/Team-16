package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.layout.Column
import kotlinx.serialization.Serializable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Serializable
object Tracker

@Composable
fun TrackerPage(navController: NavController, currentCalories: Float, calorieGoal: Float) {
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceBright)
                    .padding(vertical = 60.dp)
            ) {
                Text(
                    "Tracker",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            CalorieProgress(currentCalories = currentCalories, calorieGoal = calorieGoal)
            
            Button(
                onClick = { /*navigate to food search page */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(text = "Add Food")
            }
            
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = MaterialTheme.colorScheme.surfaceBright),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "food goes here",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
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
