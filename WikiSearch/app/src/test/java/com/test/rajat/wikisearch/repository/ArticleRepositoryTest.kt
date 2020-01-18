package com.test.rajat.wikisearch.repository

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.test.rajat.wikisearch.MockTestUtil.Companion.mockArticle
import com.test.rajat.wikisearch.MockTestUtil.Companion.mockArticleList
import com.test.rajat.wikisearch.network.WikiSearchResponseService
import com.test.rajat.wikisearch.api.ApiUtil.successCall
import com.test.rajat.wikisearch.model.*
import com.test.rajat.wikisearch.network.Resource
import com.test.rajat.wikisearch.room.ArticleDao
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ArticleRepositoryTest {

    private lateinit var repository: WikiSearchRepository
    private val articleDao = mock<ArticleDao>()
    private val service = mock<WikiSearchResponseService>()

    @Before
    fun init() {
        repository = WikiSearchRepository(
            service,
            articleDao
        )
    }

    @Test
    fun loadArticleListFromNetwork() {
        val loadFromDB = mockArticle()
        whenever(articleDao.getArticle(0)).thenReturn(loadFromDB)
        val mockResponse = WikiResponse(true, Continue(10,"continue"),Query(emptyList()))

        val call = successCall(mockResponse)
        whenever(service.searchResult("","10","")).thenReturn(call)

        val data = repository.getSearchedList("",true)
        verify(articleDao).getArticle(0)
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<Article>>>>()
        data.observeForever(observer)
        verify(observer).onChanged(Resource.success(mockArticleList(), true))

        val updatedArticle = mockArticle()
        updatedArticle.source = mockArticleList()[1].toString()
        verify(articleDao).updateArticle(updatedArticle)
    }
}