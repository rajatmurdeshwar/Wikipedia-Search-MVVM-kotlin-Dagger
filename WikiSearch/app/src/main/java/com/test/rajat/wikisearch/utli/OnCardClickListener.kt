package com.test.rajat.wikisearch.utli

import android.widget.ImageView
import android.widget.TextView
import com.test.rajat.wikisearch.model.SearchResult

interface OnCardClickListener {
    fun onClick(articleDetails:SearchResult)
}