package com.tigran.applications.newsapplication.presentation.newsdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigran.applications.newsapplication.domain.model.ArticleModel
import com.tigran.applications.newsapplication.domain.repository.NewsArticleRepository
import com.tigran.applications.newsapplication.presentation.newsdetails.uistate.NewsDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NEWS_ARTICLE_ID_KEY = "newsArticleIdKey"

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val newsArticleRepository: NewsArticleRepository
) : ViewModel() {

    private val _newsArticleUiState: MutableStateFlow<NewsDetailsUiState> by lazy {
        val newsArticleId = savedStateHandle.get<String>(NEWS_ARTICLE_ID_KEY)
        if (newsArticleId != null) {
            MutableStateFlow(NewsDetailsUiState(id = newsArticleId)).also { stateFlow ->
                viewModelScope.launch {
                    val uiState =
                        newsArticleRepository.getNewsArticle(newsArticleId)?.toNewsDetailsUiState()

                    if (uiState != null) {
                        stateFlow.value = uiState
                    } else {
                        MutableStateFlow(
                            NewsDetailsUiState(
                                id = "",
                                errorMessage = "Unexpected error"
                            )
                        )
                    }
                }
            }
        } else {
            MutableStateFlow(NewsDetailsUiState(id = "", errorMessage = "Unexpected error"))
        }
    }

    val newsArticleUiState = _newsArticleUiState.asStateFlow()

    private fun ArticleModel.toNewsDetailsUiState() =
        NewsDetailsUiState(
            id = this.id,
            author = this.author ?: "",
            description = this.description ?: "",
            publishedAt = this.publishedAt ?: "",
            title = this.title ?: "",
            urlToImage = this.urlToImage ?: ""
        )
}