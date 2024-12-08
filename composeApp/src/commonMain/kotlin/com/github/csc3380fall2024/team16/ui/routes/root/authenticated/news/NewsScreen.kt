package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.github.csc3380fall2024.team16.model.NewsArticle
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.until

@Composable
fun NewsScreen(state: NewsState) {
    
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val showBackToTopButton by remember {
        derivedStateOf { scrollState.value >= scrollState.maxValue - 50 }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            FunFactOfDay()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (state is NewsState.Loaded) {
                state.articles.forEach { (query, articles) ->
                    NewsSection(
                        sectionTitle = query,
                        articles = articles
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(45.dp)) // Add space after all articles
            
        }
        
        // Back to Top button
        if (showBackToTopButton) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    // .offset(y = 10.dp) // Positive value moves button down
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(0)
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = "Back to Top",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
        
    }
}

@Composable
fun FunFactOfDay() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fun Fact of the Day",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Did you know? Regularly stretching can improve your brain health.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun NewsSection(sectionTitle: String, articles: List<NewsArticle>) {
    Column {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            articles.forEach {
                NewsCard(it)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun NewsCard(article: NewsArticle) {
    val uriHandler = LocalUriHandler.current
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                uriHandler.openUri(article.url)
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            Modifier.padding(16.dp).height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(Modifier.weight(4f)) {
                Text(
                    text = article.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(Modifier.weight(0.1f))
            Column(
                Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .align(Alignment.Start),
                    model = article.thumbnailUrl,
                    contentDescription = null,
                )
                
                val hours = article.publishedAt.until(Clock.System.now(), DateTimeUnit.HOUR)
                val minutes = article.publishedAt.until(Clock.System.now(), DateTimeUnit.MINUTE)
                
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = when {
                        hours > 1L    -> "$hours hours ago"
                        hours == 1L   -> "1 hour ago"
                        minutes > 1L  -> "$minutes minutes ago"
                        minutes == 1L -> "1 minute ago"
                        else          -> "Just now"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    }
}
