package com.example.taipeitravel.data.repo

import com.example.taipeitravel.data.model.News
import com.example.taipeitravel.data.src.HttpService

interface TravelRepo {
    suspend fun getNews(lang: String): List<News>
}

class TravelRepoImpl(
    val httpService: HttpService
) : TravelRepo {
    override suspend fun getNews(lang: String): List<News> {
        return httpService.getNews(lang).news.subList(0, 3)
    }
}