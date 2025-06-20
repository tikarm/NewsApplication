package com.tigran.applications.newsapplication.presentation.sourcepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.toUri
import com.tigran.applications.newsapplication.presentation.sourcepage.uistate.ArticleUiState

@Composable
fun SourceArticlesScreen(
    viewModel: SourceArticlesViewModel = hiltViewModel()
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

    LazyColumn(
        state = listState,
    ) {
        items(
            count = uiState.articles.size,
            key = { uiState.articles[it].id }
        ) { index ->
            val article = uiState.articles[index]

            ArticleItem(article = article, isLastItem = index == uiState.articles.size - 1)
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

@Composable
private fun ArticleItem(
    article: ArticleUiState,
    isLastItem: Boolean
) {
    Column {
        Row {
            ImageThumbnail(
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

@Composable
private fun ImageThumbnail(
    modifier: Modifier = Modifier,
    uri: coil3.Uri,
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(uri)
        .memoryCacheKey(uri.path)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
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
        isLastItem = false
    )
}

