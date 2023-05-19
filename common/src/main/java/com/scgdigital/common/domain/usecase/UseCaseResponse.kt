package com.scgdigital.common.domain.usecase

import com.scgdigital.common.domain.model.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

