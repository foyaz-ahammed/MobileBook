package com.play2pay.bookapp.repository.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class BookItem (
    @Json(name = "title") val title: String,
    @Json(name = "imageURL") val imageUrl: String = "",
    @Json(name = "author") val author: String = "",
): Parcelable