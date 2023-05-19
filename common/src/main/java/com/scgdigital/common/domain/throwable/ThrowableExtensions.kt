package com.scgdigital.common.domain.throwable

inline fun <T> Result<T>.getOrCustomThrow(httpThrow: (httpStatus: Int) -> Throwable?) = getOrElse {
    throw (it as? SCGDigitalThrowable.HttpError)
        ?.apiErrorResponse
        ?.status
        ?.let(httpThrow)
        ?: it
}
