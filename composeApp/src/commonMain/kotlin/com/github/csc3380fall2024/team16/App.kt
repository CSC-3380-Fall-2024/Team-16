package com.github.csc3380fall2024.team16

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.github.csc3380fall2024.team16.ui.routes.root.RootRoute
import com.github.csc3380fall2024.team16.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme(dark = true) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val client = remember { RpcClient() }
            RootRoute(client)
        }
    }
}
