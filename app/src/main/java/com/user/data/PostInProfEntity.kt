package com.user.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// must be data class
@Parcelize
data class PostInProfEntity(
    var image: Int,
    var name: String,
    var lastDate: String,
    var title: String,
    var desc: String,
    var tvLike: String,
    var tvComment: String
) : Parcelable