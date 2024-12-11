package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun SocialScreen(name: String?, profilePicture: Painter?) {
    val username = name ?: "username" // update to use real username
    val userProfilePicture = profilePicture
    
    SocialFeedPage(name = username, profilePicture = userProfilePicture)
}


@Composable
fun SocialFeedPage(
    name: String?,
    profilePicture: Any?
) {
    val scrollState = rememberScrollState()
    var showCreatePostDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(24.dp)
                    .clickable { showDialog = true }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        name?.let {
                            Text(
                                text = "Welcome, $it!",
                                fontSize = 20.sp,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
            
            repeat(10) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Video Reel $index", fontSize = 18.sp)
                }
                
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )
            }
        }
        
        FloatingActionButton(
            onClick = { showCreatePostDialog = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Create"
            )
        }
        if (showCreatePostDialog) {
            CreatePostDialog(
                onDismiss = { showCreatePostDialog = false },
                onPostCreated = { caption, image ->
                    // Handle the newly created post
                    showCreatePostDialog = false
                    // You can store or display the new post here
                    println("Post created with caption: $caption and image: $image")
                }
            )
        }
        if (showDialog) {
            ProfileDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
            )
        }
    }
}

@Composable
fun CreatePostDialog(
    onDismiss: () -> Unit,
    onPostCreated: (String, Painter?) -> Unit
) {
    var caption by remember { mutableStateOf("") }
    var image: Painter? by remember { mutableStateOf(null) }
    
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create a Post",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            TextField(
                value = caption,
                onValueChange = { caption = it },
                placeholder = { Text("Enter a caption") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    // Placeholder for image picker logic
                    // Replace this with an actual image picker implementation
                    image = null // Replace with actual Painter object from image picker
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Choose Image")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
                Button(onClick = {
                    if (caption.isNotEmpty()) {
                        onPostCreated(caption, image)
                    }
                }) {
                    Text("Post")
                }
            }
        }
    }
}

@Composable
fun ProfileDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Profile Options",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    
                    Button(
                        onClick = {
                            //implement logic
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Upload Profile Picture")
                    }
                    
                    Button(
                        onClick = {
                            //implement logic
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Logout")
                    }
                    
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Close")
                    }
                }
            }
        }
    }
}
