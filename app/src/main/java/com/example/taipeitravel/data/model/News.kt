package com.example.taipeitravel.data.model
import com.google.gson.annotations.SerializedName


data class NewsResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("data")
    val news: List<News>
)

data class News(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val content: String,
    @SerializedName("url")
    val url: String
)