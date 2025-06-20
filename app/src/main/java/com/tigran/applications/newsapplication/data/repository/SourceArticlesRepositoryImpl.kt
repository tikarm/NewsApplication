package com.tigran.applications.newsapplication.data.repository

import com.tigran.applications.newsapplication.data.remote.datasource.RemoteDataSource
import com.tigran.applications.newsapplication.data.remote.exception.ApiException
import com.tigran.applications.newsapplication.data.remote.model.Article
import com.tigran.applications.newsapplication.data.remote.model.ArticleResponse
import com.tigran.applications.newsapplication.domain.model.ArticleModel
import com.tigran.applications.newsapplication.domain.model.ArticleResponseModel
import com.tigran.applications.newsapplication.domain.repository.SourceArticlesRepository
import javax.inject.Inject

class SourceArticlesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : SourceArticlesRepository {

    @Throws(ApiException::class)
    override suspend fun getSourceArticles(
        sourceId: String,
        query: String?,
        page: Int?,
        pageSize: Int
    ): ArticleResponseModel {
        return remoteDataSource.getHeadlines(
            sourceId = sourceId,
            query = query,
            page = page,
            pageSize = pageSize
        ).toArticleResponseModel()
    }

    private fun ArticleResponse.toArticleResponseModel() =
        ArticleResponseModel(
            totalResults = this.totalResults,
            articles = this.articles.map { it.toArticleModel() }
        )

    private fun Article.toArticleModel() =
        ArticleModel(
            author = this.author,
            content = this.content,
            description = this.description,
            publishedAt = this.publishedAt,
            title = this.title,
            url = this.url,
            urlToImage = this.urlToImage
        )
}