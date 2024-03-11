package com.example.taipeitravel.data.repo

import com.example.taipeitravel.data.model.Attraction
import com.example.taipeitravel.data.model.News
import com.example.taipeitravel.data.src.HttpService

interface TravelRepo {
    suspend fun getNews(lang: String): List<News>
    suspend fun getAttractions(lang: String): List<Attraction>
    fun getAttractionById(id: Int): Attraction
}

class TravelRepoImpl(
    val httpService: HttpService
) : TravelRepo {

    private var attractions: List<Attraction> = emptyList()

    override suspend fun getNews(lang: String): List<News> {
        val response = httpService.getNews(lang)
        val news = response.news
        val total = response.total
        return if (total <= 3) { news } else { news.subList(0, 3) }
    }

    override suspend fun getAttractions(lang: String): List<Attraction> {
        return httpService.getAttractions(lang).attractions.also { attractions = it }
    }

    override fun getAttractionById(id: Int): Attraction {
        return attractions.find { it.id == id }!!
    }
}