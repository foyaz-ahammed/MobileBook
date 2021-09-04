package com.play2pay.bookapp.repository.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class BookItem (
    @Json(name = "") val title: String,
    @Json(name = "") val imageUrl: String,
    @Json(name = "") val author: String = "",
): Parcelable