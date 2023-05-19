package com.scgdigital.common.data

import com.scgdigital.common.data.networkmodel.NetworkApiErrorResponse

sealed class NetworkOutcome<out T> {
    data class Success<out T>(val value: T?) : NetworkOutcome<T>()
    data class GenericError(
        val code: Int? = null,
        val cause: Exception? = null,
        val apiErrorResponse: NetworkApiErrorResponse? = null
    ) : NetworkOutcome<Nothing>()

    data class NetworkError(val ex: Exception? = null) : NetworkOutcome<Nothing>()

    fun successOrNull() = (this as? Success)
}
