package com.scgdigital.news.domain.usecase

import com.scgdigital.common.domain.usecase.UseCase
import com.scgdigital.news.domain.model.GetNewsRequestModel
import com.scgdigital.news.domain.model.NewsResponseModel
import com.scgdigital.news.domain.repository.NewsRepository

class NewsUseCase constructor(
    private val repository: NewsRepository
) : UseCase<NewsResponseModel, Any?>() {

    override suspend fun run(params: Any?): NewsResponseModel {
        return repository.getNews(params as GetNewsRequestModel)
    }

}