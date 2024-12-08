package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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

@Composable
fun RegisterScreen(
    onRegister: (username: String, email: String, password: String) -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    
    // State variables for showing password
    var isPasswordVisible by remember { mutableStateOf(false) }
    
    // State variables to track empty fields
    var isUsernameEmpty by remember { mutableStateOf(false) }
    var isEmailEmpty by remember { mutableStateOf(false) }
    var isPasswordEmpty by remember { mutableStateOf(false) }
    var isConfirmPasswordEmpty by remember { mutableStateOf(false) }
    
    // Regex pattern for email validation
    val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    
    // Regex pattern for username validation (letters, numbers, underscores)
    val usernamePattern = Regex("^[a-zA-Z0-9_]*$")
    
    Column(
        Modifier.fillMaxSize().padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
    ) {
        Text("Welcome Onboard!", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Take the first step with Universal Fitness.")
        
        Column(
            Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            // Username TextField
            TextField(
                value = username,
                onValueChange = {
                    username = it
                    isUsernameEmpty = false // Reset error when user starts typing
                },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )
            if (isUsernameEmpty) {
                Text(
                    "*Required",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            // Error message display
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        
        Button(
            onClick = {
                isUsernameEmpty = username.isEmpty()
                isEmailEmpty = email.isEmpty()
                isPasswordEmpty = password.isEmpty()
                isConfirmPasswordEmpty = confirmPassword.isEmpty()
                
                if (isUsernameEmpty || isEmailEmpty || isPasswordEmpty || isConfirmPasswordEmpty) {
                    errorMessage = "Please fill in all required fields."
                } else if (!usernamePattern.matches(username)) {
                    errorMessage = "Username can only contain letters, numbers, and underscores."
                } else if (!emailPattern.matches(email)) {
                    errorMessage = "Please enter a valid email address."
                } else if (password != confirmPassword) {
                    errorMessage = "Passwords do not match."
                } else {
                    errorMessage = ""  // Clear error message
                    onRegister(username.lowercase(), email, password) // Pass lowercase username
                }
            },
            Modifier.fillMaxWidth().padding(20.dp)
        ) {
            Text("Register", fontSize = 20.sp, lineHeight = 5.sp)
        }
    }
}
