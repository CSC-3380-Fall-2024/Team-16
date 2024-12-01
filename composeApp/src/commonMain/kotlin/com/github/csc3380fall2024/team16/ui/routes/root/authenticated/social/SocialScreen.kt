package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SocialScreen() {
    var showProfileCreation by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profilePicture: Painter? by remember { mutableStateOf(null) } // Placeholder for image handling
    
    if (showProfileCreation) {
        ProfileCreationScreen(
            onProfileCreated = { createdName, createdBio, createdPicture ->
                name = createdName
                bio = createdBio
                profilePicture = createdPicture
                showProfileCreation = false
            },
            onSkip = {
                showProfileCreation = false
            }
        )
    } else {
        SocialFeedPage(name, bio, profilePicture)
    }
}

@Composable
fun ProfileCreationScreen(
    onProfileCreated: (String, String, Painter?) -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Your Profile",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        var name by remember { mutableStateOf("") }
        var bio by remember { mutableStateOf("") }
        
        // Profile Picture (Placeholder)
        Button(
            onClick = { /* Logic to pick an image */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload Profile Picture")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Name Input
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Bio Input
        TextField(
            value = bio,
            onValueChange = { bio = it },
            placeholder = { Text("Enter your bio") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onSkip) {
                Text("Skip")
            }
            Button(onClick = { onProfileCreated(name, bio, null) }) {
                Text("Save")
            }
        }
    }
}

@Composable
fun SocialFeedPage(
    name: String?,
    bio: String?,
    profilePicture: Painter?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display Profile Information if available
        profilePicture?.let {
            Image(painter = it, contentDescription = null, modifier = Modifier.size(100.dp))
        }
        
        // Safely check for null and display name
        name?.takeIf { it.isNotEmpty() }?.let {
            Text(text = "Welcome, $it!", fontSize = 20.sp)
        }
        
        // Safely check for null and display bio
        bio?.takeIf { it.isNotEmpty() }?.let {
            Text(text = it, style = MaterialTheme.typography.bodyLarge)
        }
        
        // Social Feed Placeholder
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "This is the social feed!")
    }
}
