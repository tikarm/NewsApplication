package com.tigran.applications.newsapplication.data.di

import android.content.Context
import androidx.room.Room
import com.tigran.applications.newsapplication.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    private const val DATABASE_NAME = "articles"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context) = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    fun provideArticlesDap(db: AppDatabase) = db.articlesDao()
}