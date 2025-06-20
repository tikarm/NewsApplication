package com.tigran.applications.newsapplication.domain.model

data class ArticleModel(
    val id: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)