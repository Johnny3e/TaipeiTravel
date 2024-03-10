package com.example.taipeitravel.data.repo

import com.example.taipeitravel.data.model.Attraction
import com.example.taipeitravel.data.model.News
import com.example.taipeitravel.data.src.HttpService

interface TravelRepo {
    suspend fun getNews(lang: String): List<News>
    suspend fun getAttractions(lang: String): List<Attraction>
}

class TravelRepoImpl(
    val httpService: HttpService
) : TravelRepo {


    override suspend fun getNews(lang: String): List<News> {
        return httpService.getNews(lang).news.subList(0, 3)
    }

    override suspend fun getAttractions(lang: String): List<Attraction> {
        return httpService.getAttractions(lang).attractions
    }
}