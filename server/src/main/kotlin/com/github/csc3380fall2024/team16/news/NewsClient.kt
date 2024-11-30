package com.github.csc3380fall2024.team16.news

import com.github.csc3380fall2024.team16.model.NewsArticle
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.headers
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

class NewsClient(private val apiKey: String) {
	private val client = HttpClient(CIO) {
		headers {
			append("Ocp-Apim-Subscription-Key", apiKey)
		}
	}
	
	suspend fun getNewsArticles(query: String): List<NewsArticle> {
		val url = URLBuilder("https://api.bing.microsoft.com/v7.0/news/search").apply {
			parameters["q"] = query
			parameters["mkt"] = "en-US"
			parameters["freshness"] = "Day"
			parameters["count"] = "10"
		}.build()
		
		val resp: BingNewsAnswer = client.get(url).body()
		return resp.value.map {
			NewsArticle(
				name = it.name,
				description = it.description,
				source = "TODO",
				publishedAt = Instant.parse(it.datePublished),
				url = it.url,
				thumbnailUrl = it.image.thumbnail,
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
	val image: BingImage,
)

@Serializable
private data class BingOrganization(val name: String)

@Serializable
private data class BingImage(val thumbnail: String)

