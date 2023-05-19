package com.scgdigital.news.data.repository

import com.scgdigital.news.data.api.ApiService
import com.scgdigital.news.domain.model.GetNewsRequestModel
import com.scgdigital.news.domain.model.NewsResponseModel
import com.scgdigital.news.domain.repository.NewsRepository

class NewsRepositoryImpl(private val apiService: ApiService) : NewsRepository {
    override suspend fun getNews(getNewsRequestModel: GetNewsRequestModel): NewsResponseModel {
        return apiService.getNews(
            getNewsRequestModel.query,
            getNewsRequestModel.from,
            getNewsRequestModel.sortBy,
            getNewsRequestModel.apiKey
        )
    }
}