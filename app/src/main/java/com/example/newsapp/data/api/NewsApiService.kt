package com.example.newsapp.data.api

import com.example.newsapp.data.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = "dd072d1bc6a64199b029e45e6cff19cd"
    ): News

    @GET("everything")
    suspend fun searchNews(
        @Query("page") page: Int = 1,
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = "dd072d1bc6a64199b029e45e6cff19cd"
    ): Response<News>
}