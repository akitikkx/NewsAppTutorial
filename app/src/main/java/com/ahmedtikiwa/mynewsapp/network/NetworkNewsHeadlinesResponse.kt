package com.ahmedtikiwa.mynewsapp.network

import com.ahmedtikiwa.mynewsapp.domain.Article
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkNewsHeadlinesResponseContainer(val articles: List<NetworkArticle>)

@JsonClass(generateAdapter = true)
data class NetworkNewsHeadlinesResponse(
    val articles: List<NetworkArticle>,
    val status: String,
    val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class NetworkArticle(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: NetworkSource,
    val title: String,
    val url: String,
    val urlToImage: String?
)

@JsonClass(generateAdapter = true)
data class NetworkSource(
    val id: String?,
    val name: String?
)

fun NetworkNewsHeadlinesResponseContainer.asDomainModel(): List<Article> {
    return articles.map { networkArticle ->
        Article(
            url = networkArticle.url,
            author = networkArticle.author,
            content = networkArticle.content,
            description = networkArticle.description,
            publishedAt = networkArticle.publishedAt,
            source = networkArticle.source.name,
            title = networkArticle.title,
            urlToImage = networkArticle.urlToImage
        )
    }
}