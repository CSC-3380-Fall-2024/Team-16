package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.social

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.github.csc3380fall2024.team16.Post
import com.github.csc3380fall2024.team16.ui.components.ErrWrap
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url

@Composable
fun SocialScreen(
    name: String,
    profilePicture: Painter?,
    posts: List<Post>,
    onCreatePost: (String, ByteArray) -> Unit,
    backendUrl: Url,
    onLogout: () -> Unit,
    error: String?,
) {
    ErrWrap(error) {
        SocialFeedPage(
            name = name,
            profilePicture = profilePicture,
            posts = posts,
            onCreatePost = onCreatePost,
            backendUrl = backendUrl,
            onLogout = onLogout,
        )
    }
}


@Composable
fun SocialFeedPage(
    name: String,
    profilePicture: Any?,
    posts: List<Post>,
    onCreatePost: (String, ByteArray) -> Unit,
    backendUrl: Url,
    onLogout: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var showCreatePostDialog by remember { mutableStateOf(false) }
    var showProfileDialog by remember { mutableStateOf(false) }
    
    Box(Modifier.fillMaxSize().padding(16.dp)) {
        Column(Modifier.verticalScroll(scrollState).fillMaxSize().padding(16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(24.dp)
                    .clickable { showProfileDialog = true }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                    )
                    
                    Text(name, fontSize = 20.sp)
                }
            }
            
            if (posts.isEmpty()) {
                Text(
                    text = "No posts yet.",
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            
            posts.forEach {
                Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    HorizontalDivider()
                    
                    Text(text = it.user, style = MaterialTheme.typography.labelSmall)
                    Text(it.description)
                    
                    AsyncImage(
                        model = URLBuilder(backendUrl).apply {
                            protocol = URLProtocol.HTTP
                            pathSegments = listOf("post_image", it.id.toString())
                        }.buildString(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth,
                    )
                }
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
                onClose = { showCreatePostDialog = false },
                onCreatePost = onCreatePost,
            )
        }
        if (showProfileDialog) {
            ProfileDialog(
                onLogout = onLogout,
                onClose = { showProfileDialog = false },
            )
        }
    }
}

@Composable
fun CreatePostDialog(
    onClose: () -> Unit,
    onCreatePost: (String, ByteArray) -> Unit
) {
    var caption by remember { mutableStateOf("") }
    var image: ByteArray? by remember { mutableStateOf(null) }
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = rememberCoroutineScope(),
        onResult = { image = it.firstOrNull() }
    )
    
    Dialog(onDismissRequest = { onClose() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
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
            
            if (image != null) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                )
            }
            
            Button(
                onClick = { singleImagePicker.launch() },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Choose Image") }
            
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button({ onClose() }) { Text("Cancel") }
                Button(
                    enabled = caption.isNotBlank() && image != null,
                    onClick = {
                        onClose()
                        onCreatePost(caption, image!!)
                    },
                ) { Text("Post") }
            }
        }
    }
}

@Composable
fun ProfileDialog(
    onLogout: () -> Unit,
    onClose: () -> Unit,
) {
    Dialog(onDismissRequest = onClose) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
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
            ) { Text(text = "Upload Profile Picture") }
            
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) { Text(text = "Logout") }
            
            TextButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { Text(text = "Close") }
        }
    }
}
