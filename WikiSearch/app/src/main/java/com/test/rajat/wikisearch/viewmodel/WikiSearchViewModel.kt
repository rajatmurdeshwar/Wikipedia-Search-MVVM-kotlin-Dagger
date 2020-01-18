package com.test.rajat.wikisearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.test.rajat.wikisearch.repository.WikiSearchRepository
import com.test.rajat.wikisearch.model.Article
import com.test.rajat.wikisearch.network.Resource
import javax.inject.Inject

class WikiSearchViewModel @Inject
 constructor(private val repository: WikiSearchRepository) : ViewModel(){

    private val articleRequestData: MutableLiveData<String> = MutableLiveData()
    private val articleResponseData: LiveData<Resource<List<Article>>>

    init {
        articleResponseData = Transformations.switchMap(articleRequestData) {
            articleRequestData.value.let {
                repository.getSearchedList(it.toString(),true)

            }
        }
    }

    fun requestSearch(request: String) = articleRequestData.postValue(request)
    fun requestData() = articleResponseData
}