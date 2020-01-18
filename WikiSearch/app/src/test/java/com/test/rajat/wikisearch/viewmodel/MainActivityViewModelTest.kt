package com.test.rajat.wikisearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.test.rajat.wikisearch.MockTestUtil.Companion.mockArticle
import com.test.rajat.wikisearch.repository.WikiSearchRepository
import com.test.rajat.wikisearch.network.WikiSearchResponseService
import com.test.rajat.wikisearch.api.ApiUtil
import com.test.rajat.wikisearch.model.*
import com.test.rajat.wikisearch.network.Resource
import com.test.rajat.wikisearch.room.ArticleDao
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActivityViewModelTest {

    private lateinit var viewModel: WikiSearchViewModel
    private lateinit var articleRepository: WikiSearchRepository

    private val articleDao = mock<ArticleDao>()
    private val service = mock<WikiSearchResponseService>()

    @Before
    fun init() {
        articleRepository =
            WikiSearchRepository(
                service,
                articleDao
            )
        viewModel = WikiSearchViewModel(articleRepository)
    }

    @Test
    fun loadArticleList() {
        val loadFromDB = MutableLiveData<List<Article>>()
        whenever(articleDao.getAllArticles()).thenReturn(loadFromDB)
        val mockResponse = WikiResponse(true, Continue(10,"continue"), Query(emptyList()))
        val call = ApiUtil.successCall(mockResponse)
        whenever(service.searchResult ("Queens","10","Queens")).thenReturn(call)

        val data = viewModel.requestData()
        val observer = mock<Observer<Resource<List<Article>>>>()
        data.observeForever(observer)

        viewModel.requestSearch("Queens")
        verify(articleDao).getAllArticles()
        verifyNoMoreInteractions(service)

        val mockArticleList = ArrayList<Article>()
        mockArticleList.add(mockArticle())
        loadFromDB.postValue(mockArticleList)
        verify(observer).onChanged(
            Resource.success(viewModel.requestData().value!!.data,true)
        )
    }
}