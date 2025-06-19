package com.tigran.applications.newsapplication.data.di

import com.tigran.applications.newsapplication.data.repository.NewsSourceRepositoryImpl
import com.tigran.applications.newsapplication.domain.repository.NewsSourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class NewsSourceDataModule {
    @Binds
    abstract fun provideNewsSourceRepository(repository: NewsSourceRepositoryImpl): NewsSourceRepository
}