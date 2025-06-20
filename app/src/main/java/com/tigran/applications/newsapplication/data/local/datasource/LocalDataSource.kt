package com.tigran.applications.newsapplication.data.local.datasource

import com.tigran.applications.newsapplication.data.local.dao.ArticlesDao
import com.tigran.applications.newsapplication.data.local.entity.ArticleEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val articlesDao: ArticlesDao
) {
    suspend fun getArticles() = articlesDao.getAll()

    suspend fun insertArticles(articles: List<ArticleEntity>) = articlesDao.insertAll(articles)

    suspend fun getArticleById(id: String) = articlesDao.getArticleById(id)

    suspend fun deleteAll() = articlesDao.deleteAll()
}