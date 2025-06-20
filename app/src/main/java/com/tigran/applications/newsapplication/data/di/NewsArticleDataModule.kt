package com.tigran.applications.newsapplication.data.di

import com.tigran.applications.newsapplication.data.repository.NewsArticleRepositoryImpl
import com.tigran.applications.newsapplication.domain.repository.NewsArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class NewsArticleDataModule {
    @Binds
    abstract fun provideNewsSourceRepository(repository: NewsArticleRepositoryImpl): NewsArticleRepository
}