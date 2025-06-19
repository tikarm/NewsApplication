package com.tigran.applications.newsapplication.presentation.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class NewsSourceUiState(
    val id: String,
    val name: String,
    val description: String,
)