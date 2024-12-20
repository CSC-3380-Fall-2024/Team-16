package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    state: RegisterState,
    onRegister: (username: String, email: String, password: String, confirmPassword: String) -> Unit,
    onNavigateLogin: () -> Unit,
) {
    if (state is RegisterState.Success) {
        Column(
            Modifier.fillMaxSize().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        ) {
            Text("Registration Successful!", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("You may now log in.")
            
            Button(onClick = onNavigateLogin) {
                Text("Go to Login")
            }
        }
        return
    }
    
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    // State variables for showing password
    var isPasswordVisible by remember { mutableStateOf(false) }
    
    // State variables to track empty fields
    var isUsernameEmpty by remember { mutableStateOf(false) }
    var isEmailEmpty by remember { mutableStateOf(false) }
    var isPasswordEmpty by remember { mutableStateOf(false) }
    var isConfirmPasswordEmpty by remember { mutableStateOf(false) }
    
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
            
            // Email TextField
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailEmpty = false // Reset error when user starts typing
                },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )
            if (isEmailEmpty) {
                Text(
                    "*Required",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            // Password TextField
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordEmpty = false // Reset error when user starts typing
                },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                visualTransformation = if (isPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation()
            )
            if (isPasswordEmpty) {
                Text(
                    "*Required",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            // Confirm Password TextField
            TextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    isConfirmPasswordEmpty = false // Reset error when user starts typing
                },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Confirm Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                visualTransformation = if (isPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation()
            )
            if (isConfirmPasswordEmpty) {
                Text(
                    "*Required",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            if (state is RegisterState.Ready && state.lastError != null) {
                Text(
                    text = state.lastError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Checkbox(
                    checked = isPasswordVisible,
                    onCheckedChange = { isPasswordVisible = it }
                )
                Text(
                    text = "Show Password",
                    modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible },
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        
        Button(
            onClick = { onRegister(username, email, password, confirmPassword) },
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            enabled = state !is RegisterState.InProgress,
        ) {
            Text("Register", fontSize = 20.sp, lineHeight = 5.sp)
        }
        
        Text(
            text = "Already have an account?",
            modifier = Modifier.clickable(onClick = onNavigateLogin)
        )
    }
}
