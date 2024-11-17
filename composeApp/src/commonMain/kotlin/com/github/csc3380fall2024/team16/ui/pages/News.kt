package com.github.csc3380fall2024.team16.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.csc3380fall2024.team16.ui.theme.AppTheme
import kotlinx.serialization.Serializable

@Serializable
object News

@Composable
fun NewsPage(navController: NavController) {
    AppTheme {
        // Wrap everything in a Box to ensure no constraints are applied to the scrollable area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()) // Enable vertical scrolling
            ) {
                // Fun Fact of the Day
                FunFactOfDay()
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // The Latest News
                NewsSection(
                    sectionTitle = "The Latest News",
                    newsItems = listOf("Article 1", "Article 2", "Article 3")
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Daily Sports News
                NewsSection(
                    sectionTitle = "Daily Sports News",
                    newsItems = listOf("Sports News 1", "Latest Sports Update", "Sports News 2")
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // The Science of Fitness
                NewsSection(
                    sectionTitle = "The Science of Fitness",
                    newsItems = listOf(
                        "Fitness Science 1",
                        "Research Update on Fitness",
                        "Benefits of Strength Training"
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Discover More Button
                DiscoverMoreButton()
                
                Spacer(modifier = Modifier.height(64.dp))
                
                // Additional space to ensure everything fits within the scrollable area
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
fun DiscoverMoreButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Button(
            onClick = { /* Future feature implementation */ },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Discover More",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
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
fun NewsSection(sectionTitle: String, newsItems: List<String>) {
    Column {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            newsItems.forEach { news ->
                NewsCard(
                    newsTitle = news,
                    onReadMoreClick = {
                        //When the news API is implemented, replace the onReadMoreClick action to:
                        //
                        //-Navigate to a detailed article page using a NavController.
                        //-Open a URL in a browser.
                        //-Fetch more details about the article and display them in-app.
                        // This is only a Placeholder for navigation or API interaction
                        println("Read more clicked for: $news")
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun NewsCard(newsTitle: String, onReadMoreClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = newsTitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = "Read more...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable(onClick = onReadMoreClick)
            )
        }
    }
}
