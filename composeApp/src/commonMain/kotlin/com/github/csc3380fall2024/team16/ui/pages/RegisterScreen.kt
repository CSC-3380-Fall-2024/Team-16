package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
