package com.tigran.applications.newsapplication.data.remote.datasource

import com.tigran.applications.newsapplication.data.remote.api.NewsApiService
import com.tigran.applications.newsapplication.data.remote.exception.ApiException
import com.tigran.applications.newsapplication.data.remote.model.Article
import com.tigran.applications.newsapplication.data.remote.model.NewsSource

private const val API_KEY = "649f61a3-e5bd-4044-8021-d3edb05b0b56"
private const val STATUS_CODE_OK = "ok"
private const val STATUS_CODE_ERROR = "error"

open class RemoteDataSource(
    private val apiService: NewsApiService
) {
    @Throws(ApiException::class)
    suspend fun getSources(): List<NewsSource> {
        val response = apiService.getSources(API_KEY)
        if (response.status == STATUS_CODE_OK) {
            return response.sources
        }
        throw ApiException("Response is Empty")
    }

    @Throws(ApiException::class)
    suspend fun getHeadlines(sourceId: String, page: Int = 1, pageSize: Int = 30): List<Article> {
        val response = apiService.getHeadlines(
            sourceId = sourceId,
            page = page,
            pageSize = pageSize,
            apiKey = API_KEY
        )
        if (response.status == STATUS_CODE_OK) {
            return response.articles
        }
        throw ApiException("Failed to fetch headlines")
    }
}