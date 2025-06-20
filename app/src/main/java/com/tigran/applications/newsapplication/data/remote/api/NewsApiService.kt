package com.tigran.applications.newsapplication.data.remote.api

import com.tigran.applications.newsapplication.data.remote.model.ArticleResponse
import com.tigran.applications.newsapplication.data.remote.model.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String
    ): SourceResponse

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("sources") sourceId: String,
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int,
        @Query("q") query: String? = null,
        @Query("apiKey") apiKey: String
    ): ArticleResponse
}
