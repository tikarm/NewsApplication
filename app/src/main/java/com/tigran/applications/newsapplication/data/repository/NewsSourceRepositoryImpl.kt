package com.tigran.applications.newsapplication.data.repository

import com.tigran.applications.newsapplication.data.remote.datasource.RemoteDataSource
import com.tigran.applications.newsapplication.data.remote.exception.ApiException
import com.tigran.applications.newsapplication.data.remote.model.NewsSource
import com.tigran.applications.newsapplication.domain.model.NewsSourceModel
import com.tigran.applications.newsapplication.domain.repository.NewsSourceRepository
import javax.inject.Inject

class NewsSourceRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : NewsSourceRepository {

    @Throws(ApiException::class)
    override suspend fun getNewsSources(): List<NewsSourceModel> {
        val newsSources = remoteDataSource.getSources()

        return newsSources.map { it.toNewsSourceModel() }
    }

    private fun NewsSource.toNewsSourceModel(): NewsSourceModel =
        NewsSourceModel(
            id = this.id,
            name = this.name,
            description = this.description,
            url = this.url,
            category = this.category,
            language = this.language,
            country = this.country
        )
}