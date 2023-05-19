package com.scgdigital.news.domain.repository

import com.scgdigital.news.domain.model.GetNewsRequestModel
import com.scgdigital.news.domain.model.NewsResponseModel

interface NewsRepository {
    suspend fun getNews(getNewsRequestModel: GetNewsRequestModel): NewsResponseModel
}