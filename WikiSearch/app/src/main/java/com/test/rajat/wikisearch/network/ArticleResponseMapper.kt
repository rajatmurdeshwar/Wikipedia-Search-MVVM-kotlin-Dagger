package com.test.rajat.wikisearch.network

import com.test.rajat.wikisearch.model.WikiResponse
import com.test.rajat.wikisearch.network.NetworkResponseMapper

class ArticleResponseMapper :
    NetworkResponseMapper<WikiResponse> {
    override fun onLastPage(response: WikiResponse): Boolean {
        return true
    }

}