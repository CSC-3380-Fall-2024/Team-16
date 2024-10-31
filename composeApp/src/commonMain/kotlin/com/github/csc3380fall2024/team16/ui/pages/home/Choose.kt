package com.github.csc3380fall2024.team16.ui.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import kotlinx.serialization.Serializable

@Serializable
object Choose

@Composable
fun ChoosePage(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Hello,",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "username",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(100.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Choose your Program!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = { navController.navigate(Bodybuilding) },
                            modifier = Modifier.size(125.dp)
                        ) { }
                        Text("Bodybuilding", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = { navController.navigate(Powerlifting) },
                            modifier = Modifier.size(125.dp)
                        ) { }
                        Text("Powerlifting", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = { navController.navigate(Athletics) },
                            modifier = Modifier.size(125.dp)
                        ) { }
                        Text("Athletics", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = { navController.navigate(WeightLoss) },
                            modifier = Modifier.size(125.dp)
                        ) { }
                        Text("Weight Loss", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}
