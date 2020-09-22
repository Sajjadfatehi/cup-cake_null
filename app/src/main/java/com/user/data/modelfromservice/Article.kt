package com.user.data.modelfromservice

import android.os.Parcelable
import com.article.data.ArticleDataEntity
import com.user.data.UserEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val author: Author,
    val body: String,
    val createdAt: String,
    val description: String,
    val favorited: Boolean,
    val favoritesCount: Int,
    val slug: String,
    val tagList: List<String>,
    val title: String,
    val updatedAt: String
) : Parcelable{
    fun mapToEntity(): ArticleDataEntity {
        return ArticleDataEntity(
            slug = slug,
            body = body ?: "",
            createdAt = createdAt,
            aothorusername = author.username,
            description = description ?: "",
            favorited = favorited,
            favoritesCount = favoritesCount,
            title = title,
            updatedAt = updatedAt
        )
    }
    fun mapToUserEntity(): UserEntity {
        return UserEntity(
            username = author.username,
            image = author.image,
            following = author.following
        )
    }
}
