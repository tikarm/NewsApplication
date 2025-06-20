package com.tigran.applications.newsapplication.domain.model

data class ArticleResponseModel(
    val totalResults: Int,
    val articles: List<ArticleModel>
)
