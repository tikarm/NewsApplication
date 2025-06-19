package com.tigran.applications.newsapplication.data.remote.model

data class SourceResponse(
    val status: String,
    val sources: List<NewsSource>
)