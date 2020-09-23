package com.article.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    val following: Boolean,
    val image: String,
    val username: String,
    val bio: String? = null
) : Parcelable