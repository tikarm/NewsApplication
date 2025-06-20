package com.tigran.applications.newsapplication.domain.repository

import com.tigran.applications.newsapplication.domain.model.ArticleResponseModel

interface SourceArticlesRepository {
    suspend fun getSourceArticles(
        sourceId: String,
        page: Int,
        pageSize: Int
    ): ArticleResponseModel
}