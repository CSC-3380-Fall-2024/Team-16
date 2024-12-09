package com.github.csc3380fall2024.team16.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ErrWrap(error: String?, content: @Composable () -> Unit) {
    Box {
        content()
        
        if (error != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-10).dp)
                    .clip(RoundedCornerShape(80.dp)),
            ) {
                Text(
                    text = error,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(20.dp),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}
