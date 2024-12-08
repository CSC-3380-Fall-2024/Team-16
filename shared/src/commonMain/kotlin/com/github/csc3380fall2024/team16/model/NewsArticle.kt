package com.github.csc3380fall2024.team16.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NewsArticle(
    val name: String,
    val description: String,
    val source: String?,
    val publishedAt: Instant,
    val url: String,
    val thumbnailUrl: String?,
)
