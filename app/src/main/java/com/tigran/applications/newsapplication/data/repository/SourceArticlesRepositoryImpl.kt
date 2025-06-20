package com.tigran.applications.newsapplication.data.repository

import com.tigran.applications.newsapplication.data.local.datasource.LocalDataSource
import com.tigran.applications.newsapplication.data.local.entity.ArticleEntity
import com.tigran.applications.newsapplication.data.remote.datasource.RemoteDataSource
import com.tigran.applications.newsapplication.data.remote.exception.ApiException
import com.tigran.applications.newsapplication.data.remote.model.Article
import com.tigran.applications.newsapplication.data.remote.model.ArticleResponse
import com.tigran.applications.newsapplication.domain.model.ArticleModel
import com.tigran.applications.newsapplication.domain.model.ArticleResponseModel
import com.tigran.applications.newsapplication.domain.repository.SourceArticlesRepository
import java.util.UUID
import javax.inject.Inject

class SourceArticlesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : SourceArticlesRepository {

    @Throws(ApiException::class)
    override suspend fun getSourceArticles(
        sourceId: String,
        query: String?,
        page: Int?,
        pageSize: Int
    ): ArticleResponseModel {
        val articleResponseModel = remoteDataSource.getHeadlines(
            sourceId = sourceId,
            query = query,
            page = page,
            pageSize = pageSize
        ).toArticleResponseModel()

        val articleEntities = articleResponseModel.articles.map { it.toArticleEntity() }
        localDataSource.insertArticles(articleEntities)

        return articleResponseModel
    }

    override suspend fun clearLocalArticles() {
        localDataSource.deleteAll()
    }

    private fun ArticleResponse.toArticleResponseModel() =
        ArticleResponseModel(
            totalResults = this.totalResults,
            articles = this.articles.map { it.toArticleModel() }
        )

    private fun Article.toArticleModel() =
        ArticleModel(
            id = UUID.randomUUID().toString(),
            author = this.author,
            content = this.content,
            description = this.description,
            publishedAt = this.publishedAt,
            title = this.title,
            url = this.url,
            urlToImage = this.urlToImage
        )

    private fun ArticleModel.toArticleEntity() =
        ArticleEntity(
            id = this.id,
            author = this.author,
            description = this.description,
            publishedAt = this.publishedAt,
            title = this.title,
            url = this.url,
            urlToImage = this.urlToImage
        )
}