package com.test.rajat.wikisearch.model

import com.test.rajat.wikisearch.network.NetworkResponseModel


data class WikiResponse(val batchComplete:Boolean,
                        val continu:Continue,
                        val query: Query): NetworkResponseModel