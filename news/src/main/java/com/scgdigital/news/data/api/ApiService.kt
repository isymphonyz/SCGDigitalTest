package com.scgdigital.news.data.api

import com.scgdigital.news.domain.model.NewsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsResponseModel
}