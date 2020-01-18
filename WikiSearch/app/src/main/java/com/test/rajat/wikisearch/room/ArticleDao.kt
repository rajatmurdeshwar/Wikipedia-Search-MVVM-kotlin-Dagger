package com.test.rajat.wikisearch.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.rajat.wikisearch.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleList(articles:List<Article>)

    @Update
    fun updateArticle(article:Article)

    @Query("SELECT * FROM ARTICLE WHERE id = :id_")
    fun getArticle(id_:Int):Article

    @Query("SELECT * FROM ARTICLE")
    fun getAllArticles(): LiveData<List<Article>>
}