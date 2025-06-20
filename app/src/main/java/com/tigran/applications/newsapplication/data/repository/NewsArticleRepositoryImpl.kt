package com.tigran.applications.newsapplication.data.repository

import com.tigran.applications.newsapplication.data.local.datasource.LocalDataSource
import com.tigran.applications.newsapplication.data.local.entity.ArticleEntity
import com.tigran.applications.newsapplication.domain.model.ArticleModel
import com.tigran.applications.newsapplication.domain.repository.NewsArticleRepository
import javax.inject.Inject

class NewsArticleRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : NewsArticleRepository {

    override suspend fun getNewsArticle(id: String): ArticleModel? {
        return localDataSource.getArticleById(id)?.toArticleModel()
    }

    private fun ArticleEntity.toArticleModel() =
        ArticleModel(
            id = this.id,
            author = this.author,
            content = this.content,
            description = this.description,
            publishedAt = this.publishedAt,
            title = this.title,
            url = this.url,
            urlToImage = this.urlToImage
        )
}