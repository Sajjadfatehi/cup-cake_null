package com.article.data

import androidx.room.Embedded
import androidx.room.Relation
import com.user.data.UserEntity

data class UserWithArticlesAndTagsView(
    @Embedded val userEntity: UserEntity,
    @Relation(
        entity = ArticleDataEntity::class,
        parentColumn = "username",
        entityColumn = "authorusername"
    )
    val articleWithTags: MutableList<ArticleWithTags>
)