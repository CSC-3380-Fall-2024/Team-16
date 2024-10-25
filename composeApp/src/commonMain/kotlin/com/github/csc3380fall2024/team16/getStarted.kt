package com.github.csc3380fall2024.team16

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.csc3380fall2024.team16.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun getStarted() {
    var showApp by remember { mutableStateOf(false) }
    
    AppTheme(dark = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            if (showApp) {
                App()
            } else {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    Box(modifier = Modifier.align(Alignment.TopStart)) {
                        Canvas(modifier = Modifier.size(100.dp)) {
                        }
                    }
                    
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = "Join Universal Fitness Today",
                            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        
                        Text(
                            text = "Universal Fitness provides users essential tools to better their health. From news articles to generated workouts, Universal Fitness has it all.",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(6.dp)
                        )
                        
                        Button(
                            onClick = { showApp = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = "Get Started",
                                fontSize = 20.sp,
                                lineHeight = 5.sp
                            )
                        }
                    }
                }
            }
        }
    }
}