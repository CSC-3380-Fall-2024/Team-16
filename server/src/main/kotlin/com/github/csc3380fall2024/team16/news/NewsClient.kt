package com.github.csc3380fall2024.team16.news

import com.github.csc3380fall2024.team16.model.NewsArticle
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class NewsClient(private val apiKey: String) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    
    suspend fun getNewsArticles(query: String): List<NewsArticle> {
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
