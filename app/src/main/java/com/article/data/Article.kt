package com.article.data

import android.os.Parcelable
import com.user.data.UserEntity



data class Article(
    val author: Author,
    val body: String? = null,
    val createdAt: String,
    val description: String? = null,
    val favorited: Boolean,
    val favoritesCount: Int,
    val slug: String,
    val tagList: List<String>,
    val title: String,
    val updatedAt: String
) {
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

    fun mapToUserEntity():UserEntity{
        return UserEntity(
            username = author.username,
            image = author.image,
            following = author.following
        )
    }

}