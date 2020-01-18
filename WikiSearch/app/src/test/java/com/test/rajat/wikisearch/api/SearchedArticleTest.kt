package com.test.rajat.wikisearch.api

import com.test.rajat.wikisearch.LiveDataTestUtil
import com.test.rajat.wikisearch.network.WikiSearchResponseService
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SearchedArticleTest: ApiAbstract<WikiSearchResponseService>() {

    private lateinit var service: WikiSearchResponseService

    @Before
    fun initService() {
        this.service = createService(WikiSearchResponseService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchWikiResponseTest() {
        enqueueResponse("/full_text_search_results.json")
        val response = LiveDataTestUtil.getValue(service.searchResult("","",""))
        assertThat(response.body?.batchComplete.toString(), CoreMatchers.`is`("true"))
        assertThat(response.body?.query!!.pages[0].title, CoreMatchers.`is`("Queens"))

    }
}