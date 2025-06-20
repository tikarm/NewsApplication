package com.tigran.applications.newsapplication.domain.repository

import com.tigran.applications.newsapplication.domain.model.ArticleModel

interface NewsArticleRepository {
    suspend fun getNewsArticle(id: String): ArticleModel?
}