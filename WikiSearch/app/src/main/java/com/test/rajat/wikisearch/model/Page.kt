package com.test.rajat.wikisearch.model

data class Page(val pageId:Int,
                val ns:Int,
                val title:String,
                val index:Int,
                val thumbnail:Thumbnail?,
                val terms:Terms?)