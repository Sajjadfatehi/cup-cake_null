package com.user.data.modelfromservice

import android.os.Parcelable
import com.user.data.UserEntity
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Author(
    val following: Boolean,
    val image: String?,
    val username: String
) : Serializable, Parcelable {
    fun mapYoUserEntity(): UserEntity {
        return UserEntity(
            username, image, following
        )
    }
}