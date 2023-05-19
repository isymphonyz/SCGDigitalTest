package com.scgdigital.news.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NewsResponseModel(
    @Json(name = "status") val status: String = "",
    @Json(name = "totalResults") val totalResults : Int = 0,
    @Json(name = "articles") val articles : List<Article>,
) : Parcelable