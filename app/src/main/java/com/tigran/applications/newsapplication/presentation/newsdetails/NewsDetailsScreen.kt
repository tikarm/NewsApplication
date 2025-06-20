package com.tigran.applications.newsapplication.presentation.newsdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.toUri
import com.tigran.applications.newsapplication.presentation.common.ErrorMessage
import com.tigran.applications.newsapplication.presentation.common.ImageCompose
import com.tigran.applications.newsapplication.presentation.newsdetails.uistate.NewsDetailsUiState

@Composable
fun NewsDetailsScreen(viewModel: NewsDetailsViewModel = hiltViewModel()) {
    val newsArticle by viewModel.newsArticleUiState.collectAsState()

    ArticleDetails(newsArticle)
}

@Composable
fun ArticleDetails(newsArticle: NewsDetailsUiState) {
    if (newsArticle.errorMessage != null) {
        ErrorMessage(
            text = newsArticle.errorMessage!!
        )
    } else {
        Column {
            if (newsArticle.title.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = newsArticle.title,
                    fontWeight = FontWeight.Bold
                )
            }

            ImageCompose(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp),
                uri = newsArticle.urlToImage.toUri(),
            )

            if (newsArticle.author.isNotEmpty()) {
                ContentText(
                    text = "Author: ${newsArticle.author}"
                )
            }


            if (newsArticle.description.isNotEmpty()) {
                ContentText(
                    text = newsArticle.description
                )
            }

            if (newsArticle.publishedAt.isNotEmpty()) {
                ContentText(
                    text = "Published At: ${newsArticle.publishedAt}"
                )
            }
        }
    }
}

@Composable
private fun ContentText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(text = text, modifier = modifier.padding(4.dp))
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailsPreview() {
    val sampleNewsArticle = NewsDetailsUiState(
        id = "1",
        author = "John Doe",
        description = "This is a brief description of the article.",
        publishedAt = "2023-10-01",
        title = "Sample News Article",
        urlToImage = "https://picsum.photos/200/300",
    )
    ArticleDetails(newsArticle = sampleNewsArticle)
}

