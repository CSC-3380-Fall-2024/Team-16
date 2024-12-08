package com.github.csc3380fall2024.team16.ui.routes.root.unauthenticated.login

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    state: LoginState,
    onLogin: (usernameOrEmail: String, password: String) -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateForgotPassword: () -> Unit,
) {
    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Column(
        Modifier.fillMaxSize().padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
    ) {
        Text("Login", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Welcome back to Universal Fitness.")
        
        Column(
            Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            TextField(
                usernameOrEmail,
                { usernameOrEmail = it },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Username or Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )
            
            TextField(
                password,
                { password = it },
                Modifier.fillMaxWidth(),
                placeholder = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
        }
        
        if (state is LoginState.Ready && state.lastError != null) {
            Text(state.lastError, color = MaterialTheme.colorScheme.error)
        }
        
        Button(
            onClick = { onLogin(usernameOrEmail, password) },
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            enabled = state !is LoginState.InProgress,
        ) {
            Text("Login", fontSize = 20.sp, lineHeight = 5.sp)
        }
        
        Text(
            text = "Register",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable {
                onNavigateRegister()
            }
        )
        
        Text(
            text = "Forgot Password",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable {
                onNavigateForgotPassword()
            }
        )
    }
}

