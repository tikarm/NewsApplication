package com.tigran.applications.newsapplication.presentation.newsdetails.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class NewsDetailsUiState(
    val id: String,
    val author: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val title: String = "",
    val urlToImage: String = "",
    var errorMessage: String? = null
)