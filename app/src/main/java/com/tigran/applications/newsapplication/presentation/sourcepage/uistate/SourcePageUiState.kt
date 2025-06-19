package com.tigran.applications.newsapplication.presentation.sourcepage.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class SourcePageUiState(
    val articles: List<ArticleUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)