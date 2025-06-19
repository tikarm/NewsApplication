package com.tigran.applications.newsapplication.presentation.newssources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigran.applications.newsapplication.domain.model.NewsSourceModel
import com.tigran.applications.newsapplication.domain.repository.NewsSourceRepository
import com.tigran.applications.newsapplication.presentation.uistate.NewsSourceListUiState
import com.tigran.applications.newsapplication.presentation.uistate.NewsSourceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository
) : ViewModel() {

    private val _newsSourceListUiState: MutableStateFlow<NewsSourceListUiState> by lazy {
        MutableStateFlow(NewsSourceListUiState(isLoading = true)).also {
            viewModelScope.launch {
                fetchNewsSources()
            }.invokeOnCompletion {
                fetchEvery(4000)
            }
        }
    }

    val newsSourceListUiState = _newsSourceListUiState.asStateFlow()

    private suspend fun fetchNewsSources() {
        try {
            val newsSourceModelList: List<NewsSourceModel> =
                newsSourceRepository.getNewsSources()
            _newsSourceListUiState.value =
                NewsSourceListUiState(sources = newsSourceModelList.map { it.toNewsSourceUiState() })
        } catch (e: Exception) {
            _newsSourceListUiState.value =
                NewsSourceListUiState(errorMessage = e.message ?: "Unknown error occurred")
        }
    }

    private fun fetchEvery(millis: Long) {
        viewModelScope.launch {
            while (true) {
                delay(millis)
                fetchNewsSources()
            }
        }
    }

    private fun NewsSourceModel.toNewsSourceUiState() =
        NewsSourceUiState(
            id = this.id,
            name = this.name,
            description = this.description
        )
}