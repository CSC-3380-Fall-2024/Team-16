package com.github.csc3380fall2024.team16

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.csc3380fall2024.team16.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    
    AppTheme(dark = true) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController, startDestination = "register") {
                composable("register") { RegisterScreen(navController) }
                composable("home") { HomeScreen(navController) }
                composable("bodybuilding") { BodybuildingPage(navController) }
                composable("powerlifting") { PowerliftingPage(navController) }
                composable("athletics") { AthleticsPage(navController) }
                composable("weightloss") { WeightLossPage(navController) }
            }
        }
    }
}

@Composable
fun RegisterScreen(navController: NavController) {
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        ) {
            Text("Welcome Onboard!", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("Take the first step with Universal Fitness.")
            
            Column(
                Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                var username by remember { mutableStateOf("") }
                TextField(
                    username,
                    { username = it },
                    Modifier.fillMaxWidth(),
                    placeholder = { Text("Username") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                
                var email by remember { mutableStateOf("") }
                TextField(
                    email,
                    { email = it },
                    Modifier.fillMaxWidth(),
                    placeholder = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                
                var password by remember { mutableStateOf("") }
                TextField(
                    password,
                    { password = it },
                    Modifier.fillMaxWidth(),
                    placeholder = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                
                var confirmPassword by remember { mutableStateOf("") }
                TextField(
                    confirmPassword,
                    { confirmPassword = it },
                    Modifier.fillMaxWidth(),
                    placeholder = { Text("Confirm Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            
            Button(
                onClick = { navController.navigate("home") },
                Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Text("Register", fontSize = 20.sp, lineHeight = 5.sp)
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
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
                            onClick = { navController.navigate("bodybuilding") },
                            modifier = Modifier.size(125.dp)
                        ) { }
                        Text("Bodybuilding", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = { navController.navigate("powerlifting") },
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
                            onClick = { navController.navigate("athletics") },
                            modifier = Modifier.size(125.dp)
                        ) { }
                        Text("Athletics", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = { navController.navigate("weightloss") },
                            modifier = Modifier.size(125.dp)
                        ) { }
                        Text("Weight Loss", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun BodybuildingPage(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.size(50.dp)) {
            Text("Back")
        }
        Text("Welcome to the Bodybuilding page", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun PowerliftingPage(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.size(50.dp)) {
            Text("Back")
        }
        Text("Welcome to the Powerlifting page", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
    }
}

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


