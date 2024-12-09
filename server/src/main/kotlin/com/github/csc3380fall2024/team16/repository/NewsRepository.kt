package com.github.csc3380fall2024.team16.repository

import com.github.csc3380fall2024.team16.model.NewsArticle
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.until
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.Collections

class NewsRepository(private val apiKey: String) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    
    private val cache = Collections.synchronizedMap(mutableMapOf<String, CachedItem<List<NewsArticle>>>())
    
    suspend fun getNewsArticles(query: String): List<NewsArticle> {
        val entry = cache[query]
        if (entry == null || entry.timestamp.until(Clock.System.now(), DateTimeUnit.HOUR) > 3) {
            val articles = fetchNewsArticles(query)
            cache[query] = CachedItem(articles)
            return articles
        } else {
            return entry.data
        }
    }
    
    private suspend fun fetchNewsArticles(query: String): List<NewsArticle> {
        val resp = client.get("https://api.bing.microsoft.com/v7.0/news/search") {
            headers {
                append("Ocp-Apim-Subscription-Key", apiKey)
            }
            url {
                parameters.append("q", query)
                parameters.append("mkt", "en-US")
                parameters.append("freshness", "Day")
                parameters.append("count", "5")
            }
        }
        
        val body: BingNewsAnswer = resp.body()
        return body.value.map {
            NewsArticle(
                name = it.name,
                description = it.description,
                source = "TODO",
                publishedAt = Instant.parse(it.datePublished),
                url = it.url,
                thumbnailUrl = it.image?.thumbnail?.contentUrl,
            )
        }
    }
}

private class CachedItem<T>(val data: T) {
    val timestamp = Clock.System.now()
}

@Serializable
private data class BingNewsAnswer(val value: List<BingNewsArticle>)

@Serializable
private data class BingNewsArticle(
    val name: String,
    val description: String,
    val provider: List<BingOrganization>,
    val datePublished: String,
    val url: String,
    val image: BingImage? = null,
)

@Serializable
private data class BingOrganization(val name: String)

@Serializable
private data class BingImage(val thumbnail: BingThumbnail)

@Serializable
private data class BingThumbnail(val contentUrl: String)
