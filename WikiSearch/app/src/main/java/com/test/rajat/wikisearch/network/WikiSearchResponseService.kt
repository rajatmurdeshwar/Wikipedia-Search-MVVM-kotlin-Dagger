package com.test.rajat.wikisearch.network

import androidx.lifecycle.LiveData
import com.test.rajat.wikisearch.model.WikiResponse
import com.test.rajat.wikisearch.network.ApiResponse
import com.test.rajat.wikisearch.utli.Utils.PREFERRED_THUMB_SIZE
import com.test.rajat.wikisearch.utli.Utils.PREFIX
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiSearchResponseService {
    //action=query"
    //+"&prop=pageimages|pageterms"
    //+"&generator=prefixsearch&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description
    //https://en.wikipedia.org/w/api.php?action=query&prop=pageimages%7Cpageterms&continue=gpsoffset%7C%7C&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpssearch=Albert%20Ei&gpslimit=10&gpsoffset=10
//action=query&
// formatversion=2&
// generator=prefixsearch&
// gpssearch=sachini&
// gpslimit=10&
// prop=pageimages%7Cpageterms&
// piprop=thumbnail&
// pithumbsize=50&
// pilimit=10&
// redirects=&
// wbptterms=description

    @GET(PREFIX + "action=query&redirects="
            + "&converttitles=&prop=pageterms|pageimages|info&piprop=thumbnail"
            + "&pilicense=any&generator=prefixsearch&gpsnamespace=0&list=search&srnamespace=0"
            + "&inprop=varianttitles"
    +"&srwhat=text&srinfo=suggestion&srprop=&sroffset=0&srlimit=1&pithumbsize=50")
    fun searchResult(@Query("gpssearch") searchQuery:String,
                     @Query("gpslimit") gpsOffset:String,
                     @Query("srsearch") repeat:String): LiveData<ApiResponse<WikiResponse>>
}