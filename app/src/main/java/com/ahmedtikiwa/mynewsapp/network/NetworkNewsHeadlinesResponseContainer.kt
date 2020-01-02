package com.ahmedtikiwa.mynewsapp.network

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
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: NetworkSource,
    val title: String,
    val url: String,
    val urlToImage: String
)

@JsonClass(generateAdapter = true)
data class NetworkSource(
    val id: Any,
    val name: String
)