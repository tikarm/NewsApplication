package com.tigran.applications.newsapplication.presentation.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class NewsSourceListUiState(
    val sources: List<NewsSourceUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)