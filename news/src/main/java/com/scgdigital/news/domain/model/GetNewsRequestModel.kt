package com.scgdigital.news.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GetNewsRequestModel(
    var query: String = "",
    var from : String = "",
    var sortBy : String = "",
    var apiKey : String = ""
) : Parcelable
