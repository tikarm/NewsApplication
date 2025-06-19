package com.tigran.applications.newsapplication.domain.repository

import com.tigran.applications.newsapplication.domain.model.NewsSourceModel

interface NewsSourceRepository {
    suspend fun getNewsSources(): List<NewsSourceModel>
}