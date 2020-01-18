package com.test.rajat.wikisearch.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.rajat.wikisearch.utli.OnCardClickListener
import com.test.rajat.wikisearch.R
import com.test.rajat.wikisearch.adapter.ArticleAdapter
import com.test.rajat.wikisearch.model.SearchResult
import com.test.rajat.wikisearch.model.Status
import com.test.rajat.wikisearch.utli.observeLiveData
import com.test.rajat.wikisearch.viewmodel.WikiSearchViewModel
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    OnCardClickListener, SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel:WikiSearchViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList:ArrayList<SearchResult>
    private lateinit var arrayAdapter:ArticleAdapter
    private lateinit var toolbar: Toolbar
    //private lateinit var mSearchItem:MenuItem
    private lateinit var searchView:SearchView
    private lateinit var internetAvailable:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        toolbar = findViewById(R.id.toolbar)
        internetAvailable = findViewById(R.id.internetAvailable)
        setSupportActionBar(toolbar)
        arrayList = ArrayList()
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(WikiSearchViewModel::class.java)
        observeViewModel()
        recyclerView.layoutManager = LinearLayoutManager(this)
        arrayAdapter = ArticleAdapter(this,arrayList,this)
        recyclerView.adapter = arrayAdapter
    }

    override fun onResume() {
        super.onResume()
        if(!isInternetAvailable(this)) {
            Timber.d("Internet Not Available")
            recyclerView.visibility = View.GONE
            internetAvailable.visibility = View.VISIBLE
        } else{
            Timber.d("Internet Available")
            internetAvailable.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        if (menu != null) {
            searchView = menu.findItem(R.id.m_search).actionView as SearchView
            searchView.setOnQueryTextListener(this)
        }
        return true
    }


    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                    
                }
            }
        }
        return result
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.requestData()) { it ->
            when(it.status) {
                Status.SUCCESS -> {
                    Timber.d("Observer Data Size "+it.data?.size)
                    arrayList.clear()
                    it.data?.forEach {
                        arrayList.add(SearchResult(it.pageId,it.title,it.source,it.terms))
                    }
                    arrayAdapter.notifyDataSetChanged()

                }
                Status.LOADING -> {
                    Timber.d("MainActivity "+"loading")
                }
                Status.ERROR -> {
                    Timber.d("MainActivity "+"error")
                }
            }

            //Timber.d("MainActivity "+it.data.toString())
            Timber.d("error "+it.errorEnvelope.toString())
            Timber.d("message "+it.message.toString())
            Timber.d("status "+it.status.toString())

        }
    }

    override fun onClick(articleDetails: SearchResult) {
        Timber.d("MainActivity onClick details $articleDetails")
        val intent = Intent(this,WikiPageActivity::class.java)
        intent.putExtra("title",articleDetails.title)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Timber.d("onQueryTextChange $newText")
        if(newText!=null && newText.isNotEmpty()) {
            viewModel.requestSearch(newText.toString())
        }else{
            arrayList.clear()
            Timber.d("Empty array")
        }
        return true

    }
}
