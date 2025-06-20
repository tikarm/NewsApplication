package com.tigran.applications.newsapplication.data.di

import com.tigran.applications.newsapplication.data.repository.SourceArticlesRepositoryImpl
import com.tigran.applications.newsapplication.domain.repository.SourceArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class SourceArticlesDataModule {
    @Binds
    abstract fun provideSourceArticles(repository: SourceArticlesRepositoryImpl): SourceArticlesRepository
}