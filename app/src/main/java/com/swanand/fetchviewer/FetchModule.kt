package com.swanand.fetchviewer

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FetchModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFetchService(retrofit: Retrofit): FetchService {
        return retrofit.create(FetchService::class.java)
    }

    @Provides
    @Singleton
    fun provideFetchRepository(api: FetchService): FetchRepository {
        return FetchRepository(api)
    }
}