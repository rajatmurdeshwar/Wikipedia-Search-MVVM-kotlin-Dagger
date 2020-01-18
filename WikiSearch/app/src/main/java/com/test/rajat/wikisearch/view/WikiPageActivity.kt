package com.test.rajat.wikisearch.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.test.rajat.wikisearch.R
import dagger.android.AndroidInjection
import timber.log.Timber

class WikiPageActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wikipage)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)
        val webview: WebView = findViewById(R.id.webView)
        val string = intent.extras?.get("title").toString()
        toolbar.title =string
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
            finish()
        }
        val title = string.replace(" ","_")
        Timber.d("onCreate $title")
        webview.loadUrl("https://en.m.wikipedia.org/wiki/$title")

    }

}