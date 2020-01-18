package com.test.rajat.wikisearch.network

import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NotNull
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun newsHeadlinesService(@NotNull retrofit: Retrofit) : WikiSearchResponseService {
        return retrofit.create(WikiSearchResponseService::class.java)
    }
}