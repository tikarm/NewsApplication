package com.tigran.applications.newsapplication.domain.model

data class NewsSourceModel(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)