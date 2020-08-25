package com.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// must be data class
@Parcelize
data class PostInProf(
    val image: Int,
    val name: String,
    val lastDate: String,
    val title: String,
    val desc: String,
    val tvLike: String,
    val tvComment: String
) : Parcelable