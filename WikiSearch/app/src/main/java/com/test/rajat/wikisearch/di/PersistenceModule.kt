package com.test.rajat.wikisearch.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.test.rajat.wikisearch.room.AppDatabase
import com.test.rajat.wikisearch.room.ArticleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application) : AppDatabase {
        return Room.databaseBuilder(application,AppDatabase::class.java,"News.db")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(@NonNull database: AppDatabase): ArticleDao {
        return database.articleDao()
    }
}