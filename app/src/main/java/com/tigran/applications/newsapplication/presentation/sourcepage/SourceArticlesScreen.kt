package com.tigran.applications.newsapplication.presentation.sourcepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.toUri
import com.tigran.applications.newsapplication.presentation.common.ErrorMessage
import com.tigran.applications.newsapplication.presentation.common.ImageCompose
import com.tigran.applications.newsapplication.presentation.sourcepage.uistate.ArticleUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SourceArticlesScreen(
    viewModel: SourceArticlesViewModel = hiltViewModel(),
    onNewsArticleClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.sourcePageUiState.collectAsState()
    var isNewPageLoading by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    val endReached: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 &&
                    lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(endReached) {
        if (endReached) {
            isNewPageLoading = true
            viewModel.onPageBottomReached {
                isNewPageLoading = false
            }
        }
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    } else if (uiState.articles.isEmpty()) {
        ErrorMessage("No articles found")
    } else if (uiState.errorMessage != null) {
        ErrorMessage(uiState.errorMessage!!)
    } else {
        LazyColumn(
            state = listState,
        ) {
            item {
                SearchBar(
                    onQueryUpdated = { newQuery ->
                        viewModel.onSearchTextUpdated(newQuery)
                    },
                    onBackPressed = {
                        viewModel.onBackPressed()
                        onBackPressed()
                    }
                )
            }
            items(
                count = uiState.articles.size,
                key = { uiState.articles[it].id }
            ) { index ->
                val article = uiState.articles[index]

                ArticleItem(
                    article = article,
                    isLastItem = index == uiState.articles.size - 1
                ) {
                    onNewsArticleClicked(it)
                }
            }

            if (isNewPageLoading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    onQueryUpdated: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    var currentQuery by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        OutlinedTextField(
            value = currentQuery,
            onValueChange = { newQuery ->
                currentQuery = newQuery

                coroutineScope.launch {
                    delay(600)
                    onQueryUpdated(newQuery)
                }
            },
            shape = CircleShape,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            placeholder = { Text("Search articles...") }
        )
    }
}

@Composable
private fun ArticleItem(
    article: ArticleUiState,
    isLastItem: Boolean,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onClick(article.id)
        }
    ) {
        Row {
            ImageCompose(
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                uri = article.urlToImage.toUri(),
            )

            Column {
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(2.dp))

                Text(
                    text = article.description,
                    fontSize = 16.sp,
                )
            }
        }

        if (!isLastItem) {
            HorizontalDivider(
                modifier = Modifier.padding(8.dp),
                thickness = 0.5.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewArticleItem() {
    ArticleItem(
        article = ArticleUiState(
            id = "1",
            title = "Sample Article",
            description = "This is a sample description of an article.",
            urlToImage = "https://picsum.photos/id/237/200/300"
        ),
        isLastItem = false,
        onClick = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun SearchBarPreview() {
    SearchBar(onQueryUpdated = {}, onBackPressed = {})
}
