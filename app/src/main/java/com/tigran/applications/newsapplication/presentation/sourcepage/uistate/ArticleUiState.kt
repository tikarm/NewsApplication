package com.tigran.applications.newsapplication.presentation.sourcepage.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class ArticleUiState(
    val id: String,
    val title: String,
    val description: String,
    val urlToImage: String
)
