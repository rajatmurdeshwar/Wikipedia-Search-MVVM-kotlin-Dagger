package com.test.rajat.wikisearch.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.rajat.wikisearch.network.ArticleResponseMapper
import com.test.rajat.wikisearch.network.WikiSearchResponseService
import com.test.rajat.wikisearch.model.Article
import com.test.rajat.wikisearch.model.WikiResponse
import com.test.rajat.wikisearch.network.ApiResponse
import com.test.rajat.wikisearch.network.Resource
import com.test.rajat.wikisearch.room.ArticleDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WikiSearchRepository @Inject
 constructor(private val searchResult: WikiSearchResponseService, val articleDao:ArticleDao): Repository{

    init {
        Timber.d("Injection WikiSearchRepository")
    }

    fun getSearchedList(queryString:String,online:Boolean): LiveData<Resource<List<Article>>> {
        return object: NetworkBoundRepository<List<Article>, WikiResponse, ArticleResponseMapper>() {
            override fun saveFetchData(items: WikiResponse) {
                Timber.d("savefetchData before "+items.query)
                if(items.query.pages!=null) {
                    val list = arrayListOf<Article>()
                    for ((id, item) in items.query.pages.withIndex()) {
                        if (item != null) {
                            val article = Article(
                                id,
                                item.pageId,
                                item.ns,
                                item.title,
                                item.index,
                                item.thumbnail?.let { it.source }.toString(),
                                item.terms?.let { it.description?.let { it[0] } }.toString()
                            )
                            Timber.d("savefetchData before $article")
                            article.let { list.add(it) }
                        }
                    }
                    articleDao.insertArticleList(list)
                }else{
                    articleDao.insertArticleList(emptyList())
                }
            }

            override fun shouldFetch(data: List<Article>?): Boolean {
                return online
            }

            override fun loadFromDb(): LiveData<List<Article>> {
                val data: MutableLiveData<List<Article>> = MutableLiveData()
                val list = arrayListOf<Article>()
                Timber.d("loadfromdatabase before")
                for(id in 0..9) {
                    val article:Article? = articleDao.getArticle(id_ = id)
                    if(article != null) {
                        list.add(Article(id,article.pageId,article.ns,article.title,article.index,article.source,article.terms))
                    }else{
                        list.add(Article(id,0,0,"",0,"",""))

                    }

                }
                Timber.d("loadfromdatabase : $list")
                data.value = list
                return data
            }

            override fun fetchService(): LiveData<ApiResponse<WikiResponse>> {
                val dat=searchResult.searchResult(queryString,"10",queryString)
                return dat
            }

            override fun mapper(): ArticleResponseMapper {
                return ArticleResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed : $message")
            }

        }.asLiveData()
    }
}