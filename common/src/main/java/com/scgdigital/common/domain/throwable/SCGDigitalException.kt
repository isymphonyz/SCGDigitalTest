package com.scgdigital.common.domain.throwable

sealed class SCGDigitalException(
    override val message: String? = null
) : Exception() {

    /**
     * this should only be thrown by Repositories
     *
     * @param message retrieved from ApolloResponse.errors[0].message.
     * Please, look into ApolloResponse class for more details
     * @param code retrieved from ApolloResponse.errors[0].extensions.code
     * */
    data class SCGDigitalError(
        override val message: String? = null,
        val code: String? = null,
        val extensions: Map<String, Any?>? = null,
    ) : SCGDigitalException()

    object NetworkError : SCGDigitalException()
}
