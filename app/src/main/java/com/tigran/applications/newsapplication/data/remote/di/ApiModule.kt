package com.tigran.applications.newsapplication.data.remote.di

import com.tigran.applications.newsapplication.data.remote.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val BASE_URL = "https://newsapi.org/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(
                okhttp3.OkHttpClient.Builder()
                    .addInterceptor(
                        okhttp3.logging.HttpLoggingInterceptor().apply {
                            level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .build()
            )
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}