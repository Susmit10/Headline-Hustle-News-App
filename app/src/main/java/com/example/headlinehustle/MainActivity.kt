package com.example.headlinehustle

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    private lateinit var mUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUrl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=545a329288564e42a8f667d36ab9f00b"


        pullToRefresh.setOnRefreshListener {
            refreshData() // your code
            pullToRefresh.isRefreshing = false
        }


        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }


    private fun fetchData()
    {
        /*
        val list = ArrayList<String>()
        for (i in 0 until 100)
        {
            list.add("Item $i")
        }
        return list

         */

//        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=545a329288564e42a8f667d36ab9f00b"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, mUrl, null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)

            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        val colorInt: Int = Color.parseColor("#ffffff") //red
        builder.setToolbarColor(colorInt)
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }

    override fun refreshData() {

        Toast.makeText(this, "Gets refreshed after 15 minutes", Toast.LENGTH_LONG).show()
        mUrl = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=545a329288564e42a8f667d36ab9f00b"
        fetchData()
        Toast.makeText(this, "Here's some science news for you", Toast.LENGTH_LONG).show()

    }

    fun refresh() {

        Toast.makeText(this, "Gets refreshed after 15 minutes", Toast.LENGTH_LONG).show()
        mUrl = "https://newsapi.org/v2/top-headlines?country=in&category=general&apiKey=545a329288564e42a8f667d36ab9f00b"
        fetchData()
        Toast.makeText(this, "Here's some general news for you", Toast.LENGTH_LONG).show()

    }

    override fun onShare(news: News) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Checkout this latest news ${news.url}"
        )
        startActivity(intent)

    }
}