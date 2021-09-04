package com.play2pay.bookapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Model class for one book item
 */
@Parcelize
data class BookItem (
    val title: String,
    val image: String,
    val author: String
): Parcelable