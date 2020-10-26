package com.example.headlinehustle

import org.json.JSONObject

data class News(
    val title: String,
    val author: String,
    val url: String,
    val urlToImage: String,
    val desc: String,
    val source: JSONObject,
    val time: String
)