package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
fun AthleticsPage(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.size(20.dp)) {
            Text("Back")
        }
        Text("Welcome to the Athletics page", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
    }
}
