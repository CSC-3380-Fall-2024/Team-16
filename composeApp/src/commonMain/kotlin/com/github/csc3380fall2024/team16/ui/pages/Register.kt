package com.github.csc3380fall2024.team16.ui.pages

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.RpcService
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Register

@Composable
fun RegisterPage(navController: NavController, client: RpcService) {
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
    
    val scope = rememberCoroutineScope()
    
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
                
                // Add error message display here
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                
                // Add a Row for the checkbox
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
                        modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
                    )
                }
            }
            
            Button(
                onClick = {
                    // Reset empty field indicators
                    isUsernameEmpty = username.isEmpty()
                    isEmailEmpty = email.isEmpty()
                    isPasswordEmpty = password.isEmpty()
                    isConfirmPasswordEmpty = confirmPassword.isEmpty()
                    
                    // Check if any field is empty
                    if (isUsernameEmpty || isEmailEmpty || isPasswordEmpty || isConfirmPasswordEmpty) {
                        errorMessage = "Please fill in all required fields."
                    }
                    // Check for valid email format
                    else if (!emailPattern.matches(email)) {
                        errorMessage = "Please enter a valid email address."
                    }
                    // Check for matching passwords
                    else if (password != confirmPassword) {
                        errorMessage = "Passwords do not match."
                    } else {
                        errorMessage = ""  // Clear error message if all validations pass
                        scope.launch {
                            try {
                                client.register(username, email, password)
                                navController.navigate(Home)
                            } catch (e: Exception) {
                                println(e) // TODO display text
                            }
                        }
                    }
                },
                Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Text("Register", fontSize = 20.sp, lineHeight = 5.sp)
            }
        }
    }
}
