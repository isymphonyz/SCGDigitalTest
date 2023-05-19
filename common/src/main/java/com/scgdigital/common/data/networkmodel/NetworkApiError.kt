package com.scgdigital.common.data.networkmodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkApiError(
    @field:Json(name = "code")
    val code: Int = 0,

    @field:Json(name = "message")
    val message: String = "Unknown",

    @field:Json(name = "detail")
    val detail: String = "Failed to parse error body",

    @field:Json(name = "fields")
    val fields: Map<String, Any>? = null
)
