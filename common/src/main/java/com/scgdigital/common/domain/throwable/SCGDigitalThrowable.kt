package com.scgdigital.common.domain.throwable

import com.scgdigital.common.data.NetworkOutcome
import com.scgdigital.common.data.networkmodel.NetworkApiErrorResponse

/**
 * don't use this for Graphql
 * Use [SCGDigitalException] for Graphql
* */
sealed class SCGDigitalThrowable(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Throwable() {

    data class HttpError(
        val apiErrorResponse: NetworkApiErrorResponse? = null,
        override val cause: Throwable? = null,
    ) : SCGDigitalThrowable() {

        override val message: String = apiErrorResponse.toString()
    }

    data class NetworkError(
        override val message: String? = null,
        override val cause: Throwable? = null,
    ) : SCGDigitalThrowable()

    data class CommonError(
        override val message: String? = null,
        override val cause: Throwable? = null,
    ) : SCGDigitalThrowable()
}

fun <T> NetworkOutcome<T>.getOrThrow(
    customThrowApiError: (apiErrorResponse: NetworkApiErrorResponse) -> Throwable? = { null }
): T =
    when (this) {
        is NetworkOutcome.GenericError -> {
            apiErrorResponse?.let {
                throw (customThrowApiError(it) ?: SCGDigitalThrowable.HttpError(it, cause))
            } ?: throw SCGDigitalThrowable.CommonError(cause = cause)
        }
        is NetworkOutcome.NetworkError -> throw SCGDigitalThrowable.NetworkError(cause = ex)
        is NetworkOutcome.Success -> this.value ?: throw SCGDigitalThrowable.CommonError()
    }

object MaxAttemptExceededException : Throwable()
object InvalidPhoneNumberException : Throwable()
object UndetectableKtpException : Throwable()
object KtpMandatoryFieldsBlank : Throwable()
