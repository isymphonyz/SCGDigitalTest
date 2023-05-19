package com.scgdigital.common.presentation.error

import com.scgdigital.common.domain.throwable.SCGDigitalThrowable

sealed class SCGDigitalError {
    object Network : SCGDigitalError()
    object Common : SCGDigitalError()
}

fun SCGDigitalThrowable.mapToError() = when (this) {
    is SCGDigitalThrowable.CommonError,
    is SCGDigitalThrowable.HttpError -> SCGDigitalError.Common
    is SCGDigitalThrowable.NetworkError -> SCGDigitalError.Network
}
