package com.tigran.applications.newsapplication.presentation.newssources

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tigran.applications.newsapplication.presentation.common.ErrorMessage
import com.tigran.applications.newsapplication.presentation.newssources.uistate.NewsSourceUiState

@Composable
fun NewsSourcesScreen(
    viewModel: NewsSourcesViewModel = hiltViewModel(),
    onSourceClicked: (String) -> Unit = {}
) {
    val uiState by viewModel.newsSourceListUiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onScreenClosed()
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
    } else if (uiState.sources.isNotEmpty()) {
        LazyColumn {
            items(
                uiState.sources.size,
                key = { uiState.sources[it].id }
            ) { index ->
                val source = uiState.sources[index]

                NewsSourceItem(
                    source = source,
                    isLast = index == uiState.sources.size - 1,
                    onSourceClicked = onSourceClicked
                )
            }
        }
    } else {
        ErrorMessage(text = uiState.errorMessage ?: "Something went wrong")
    }
}

@Composable
private fun NewsSourceItem(
    source: NewsSourceUiState,
    isLast: Boolean,
    onSourceClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onSourceClicked(source.id)
            }
    ) {
        Text(
            text = source.name,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = source.description
        )

        Spacer(modifier = Modifier.padding(4.dp))

        if (!isLast) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsSourceItem() {
    val sampleSource = NewsSourceUiState(
        id = "1",
        name = "Sample News Source",
        description = "This is a sample description for a news source."
    )

    NewsSourceItem(source = sampleSource, isLast = false, {})
}



