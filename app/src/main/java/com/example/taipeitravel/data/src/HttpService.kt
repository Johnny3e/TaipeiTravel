package com.example.taipeitravel.data.src

import com.example.taipeitravel.data.model.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface HttpService {

    @GET(ApiEndPoint.News)
    suspend fun getNews(@Path("lang") lang: String): NewsResponse

    companion object {
        fun create(): HttpService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader("Accept", "application/json").build()
                    it.proceed(request)
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiEndPoint.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create<HttpService>()
        }
    }
}