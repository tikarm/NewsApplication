package com.tigran.applications.newsapplication.data.remote.datasource

import com.tigran.applications.newsapplication.data.remote.api.NewsApiService
import com.tigran.applications.newsapplication.data.remote.exception.ApiException
import com.tigran.applications.newsapplication.data.remote.model.ArticleResponse
import com.tigran.applications.newsapplication.data.remote.model.NewsSource
import javax.inject.Inject

private const val API_KEY = "3e2201fafbb540b8b35bd4b5431d0f21"
private const val STATUS_CODE_OK = "ok"
private const val STATUS_CODE_ERROR = "error"

class RemoteDataSource @Inject constructor(
    private val apiService: NewsApiService
) {
    @Throws(ApiException::class)
    suspend fun getSources(): List<NewsSource> {
        val response = try {
            apiService.getSources(API_KEY)
        } catch (e: Exception) {
            throw ApiException("Failed to fetch sources")
        }
        if (response.status == STATUS_CODE_OK) {
            return response.sources
        }
        throw ApiException("Response is Empty")
    }

    @Throws(ApiException::class)
    suspend fun getHeadlines(
        sourceId: String,
        query: String?,
        page: Int?,
        pageSize: Int
    ): ArticleResponse {
        val response =
            try {
                apiService.getHeadlines(
                    sourceId = sourceId,
                    query = query,
                    page = page,
                    pageSize = pageSize,
                    apiKey = API_KEY
                )
            } catch (e: Exception) {
                throw ApiException("Failed to fetch headlines")
            }
        if (response.status == STATUS_CODE_OK) {
            return response
        }
        throw ApiException("Failed to fetch headlines")
    }
}