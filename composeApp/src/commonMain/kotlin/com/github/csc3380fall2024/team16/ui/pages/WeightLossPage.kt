package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WeightLossPage(navController: NavController) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.size(50.dp)) {
            Text("Back")
        }
        Text("Welcome to the Weight Loss page", fontSize = 24.sp)
    }
}
