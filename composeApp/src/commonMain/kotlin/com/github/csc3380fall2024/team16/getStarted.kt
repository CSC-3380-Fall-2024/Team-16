package com.github.csc3380fall2024.team16

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.csc3380fall2024.team16.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun getStarted() {
    AppTheme(dark = true) {
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
                        singleLine = true
                    )
                    
                    var confirmPassword by remember { mutableStateOf("") }
                    TextField(
                        confirmPassword,
                        { confirmPassword = it },
                        Modifier.fillMaxWidth(),
                        //                        shape = RoundedCornerShape(400.dp),
                        placeholder = { Text("Confirm Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )
                }
                
                Button({}, Modifier.fillMaxWidth().padding(20.dp)) {
                    Text("Register", fontSize = 20.sp, lineHeight = 5.sp)
                }
            }
        }
    }
}
