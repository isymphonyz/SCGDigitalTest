package com.scgdigital.common.data.networkmodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkApiErrorResponse(
    @field:Json(name = "data")
    val data: Map<String, Any>?,

    @field:Json(name = "status")
    val status: Int,

    @field:Json(name = "error")
    val error: NetworkApiError?
)
