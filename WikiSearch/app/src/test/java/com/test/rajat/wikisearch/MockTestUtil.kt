package com.test.rajat.wikisearch

import com.test.rajat.wikisearch.model.*

class MockTestUtil {

    companion object {
        fun mockArticle() = Article(0,121,0,"Queens",0,"","")

        fun mockArticleList() : List<Article> {
            val article = ArrayList<Article>()
            article.add(Article(0,121,0,"Queens",0,"",""))
            article.add(Article(0,121,0,"Queens",0,"",""))
            article.add(Article(0,121,0,"Queens",0,"",""))
            return article
        }
        fun mockResponseList() : List<WikiResponse> {
            val article = ArrayList<WikiResponse>()
            article.add(WikiResponse(true, Continue(10,"continue"), Query(emptyList())))
            article.add(WikiResponse(true, Continue(10,"continue"), Query(emptyList())))
            article.add(WikiResponse(true, Continue(10,"continue"), Query(emptyList())))
            article.add(WikiResponse(true, Continue(10,"continue"), Query(emptyList())))
            return article
        }

        fun mockSearchList() : List<SearchResult> {
            val article = ArrayList<SearchResult>()
            article.add(SearchResult(123,"Sachin", "",""))
            article.add(SearchResult(232,"a", "",""))
            article.add(SearchResult(113,"Albert", "",""))
            return article
        }

    }

}