package com.tigran.applications.newsapplication.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest

@Composable
fun ImageCompose(
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