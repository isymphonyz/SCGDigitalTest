package com.scgdigital.news.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "id") val id: String? = "",
    @Json(name = "name") val name: String? = ""
) : Parcelable
