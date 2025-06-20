package com.tigran.applications.newsapplication.presentation.sourcepage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigran.applications.newsapplication.data.remote.exception.ApiException
import com.tigran.applications.newsapplication.domain.model.ArticleModel
import com.tigran.applications.newsapplication.domain.model.ArticleResponseModel
import com.tigran.applications.newsapplication.domain.repository.SourceArticlesRepository
import com.tigran.applications.newsapplication.presentation.sourcepage.uistate.ArticleUiState
import com.tigran.applications.newsapplication.presentation.sourcepage.uistate.SourcePageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject

const val SOURCE_ID_KEY = "sourceIdKey"
private const val PAGE_SIZE = 5

@HiltViewModel
class SourceArticlesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val sourceArticlesRepository: SourceArticlesRepository
) : ViewModel() {

    private var currentPage = 1
    private var totalResults = 0

    private val _sourcePageUiState: MutableStateFlow<SourcePageUiState> by lazy {
        MutableStateFlow(SourcePageUiState(isLoading = true)).also {
            viewModelScope.launch {
                savedStateHandle.get<String>(SOURCE_ID_KEY)?.let { sourceId ->
                    try {
                        val articleResponseModel = fetchArticles(
                            sourceId = sourceId,
                            page = currentPage
                        )

                        totalResults = articleResponseModel.totalResults

                        updateArticlesUiState(
                            articleResponseModel.articles.map { it.toArticleUiState() }
                        )
                    } catch (e: Exception) {
                        _sourcePageUiState.value = SourcePageUiState(
                            errorMessage = e.message ?: "Unknown error occurred"
                        )
                    }
                }
            }
        }
    }

    val sourcePageUiState = _sourcePageUiState.asStateFlow()

    fun onPageBottomReached(onDone: () -> Unit) {
        viewModelScope.launch {
            if (totalResults > PAGE_SIZE && totalResults > _sourcePageUiState.value.articles.size) {
                savedStateHandle.get<String>(SOURCE_ID_KEY)?.let { sourceId ->
                    try {
                        val articleResponseModel = fetchArticles(
                            sourceId = sourceId,
                            page = ++currentPage
                        )

                        updateArticlesUiState(
                            _sourcePageUiState.value.articles +
                                    articleResponseModel.articles.map { it.toArticleUiState() }
                        )
                    } catch (e: Exception) {
                        _sourcePageUiState.value = SourcePageUiState(
                            errorMessage = e.message ?: "Unknown error occurred"
                        )
                    }
                }
            }
            onDone()
        }
    }

    fun onSearchTextUpdated(text: String) {
        viewModelScope.launch {
            try {
                val articleResponseModel = fetchArticles(
                    sourceId = savedStateHandle.get<String>(SOURCE_ID_KEY)!!,
                    query = text.takeIf { it.isNotEmpty() },
                )
                updateArticlesUiState(
                    articleResponseModel.articles.map { it.toArticleUiState() }
                )
            } catch (e: Exception) {
                _sourcePageUiState.value = SourcePageUiState(
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    @Throws(ApiException::class)
    private suspend fun fetchArticles(
        sourceId: String,
        query: String? = null,
        page: Int? = null
    ): ArticleResponseModel {
        return sourceArticlesRepository.getSourceArticles(
            sourceId = sourceId,
            query = query,
            pageSize = PAGE_SIZE,
            page = page
        )
    }

    private fun updateArticlesUiState(articleUiState: List<ArticleUiState>) {
        _sourcePageUiState.value = SourcePageUiState(
            articles = articleUiState
        )
    }

    private fun ArticleModel.toArticleUiState() =
        ArticleUiState(
            id = Random().nextInt().toString(),
            title = title ?: "",
            description = description ?: "",
            urlToImage = urlToImage ?: ""
        )
}