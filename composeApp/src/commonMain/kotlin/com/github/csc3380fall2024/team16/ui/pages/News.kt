package com.github.csc3380fall2024.team16.ui.pages

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

@Composable
fun NewsPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Enabling vertical scroll
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
            newsItems = listOf("Fitness Science 1", "Research Update on Fitness", "Benefits of Strength Training")
        )
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
                NewsCard(newsTitle = news)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun NewsCard(newsTitle: String) {
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
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
