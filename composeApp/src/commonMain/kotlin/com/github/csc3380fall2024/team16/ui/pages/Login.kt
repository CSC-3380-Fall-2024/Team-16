package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.ui.MainViewModel
import com.github.csc3380fall2024.team16.ui.theme.AppTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object Login

@Composable
@Preview
fun LoginPage(navController: NavController, viewModel: MainViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val scope = rememberCoroutineScope()
    
    AppTheme(dark = true) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            
            Column(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp),
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
                        username,
                        { username = it },
                        Modifier.fillMaxWidth(),
                        placeholder = { Text("Username") },
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
                
                Button({
                    scope.launch {
                        viewModel.login(username, password)
                        navController.navigate(Home)
                    }
                }, Modifier.fillMaxWidth().padding(20.dp)) {
                    Text("Login", fontSize = 20.sp, lineHeight = 5.sp)
                }
                
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.clickable {
                        navController.navigate(Register)
                    }
                )
                
                Text(
                    text = "Forgot Password",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.clickable {
                        navController.navigate(ForgotPassword)
                    }
                )
            }
        }
    }
}

