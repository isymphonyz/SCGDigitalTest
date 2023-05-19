package com.scgdigital.news.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "source") val source: Source? = null,
    @Json(name = "author") val author : String? = "",
    @Json(name = "title") val title : String? = "",
    @Json(name = "description") val description : String? = "",
    @Json(name = "url") val url : String? = "",
    @Json(name = "urlToImage") val urlToImage : String? = "",
    @Json(name = "publishedAt") val publishedAt : String? = "",
    @Json(name = "content") val content : String? = "",
) : Parcelable
