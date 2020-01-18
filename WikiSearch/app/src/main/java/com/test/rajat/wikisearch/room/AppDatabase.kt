package com.test.rajat.wikisearch.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.rajat.wikisearch.model.Article

@Database(entities = [Article::class],version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao():ArticleDao
}