package com.tigran.applications.newsapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tigran.applications.newsapplication.data.local.entity.ArticleEntity

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM articles")
    suspend fun getAll(): List<ArticleEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: String): ArticleEntity?

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}