package com.article.data

import androidx.room.Embedded
import androidx.room.Relation
import com.user.data.UserEntity

/**
 * Created by moha on 9/16/2020.
 */
data class ArticleUser(
    @Embedded val articleDataEntity: ArticleDataEntity,
    @Relation(
        entity = UserEntity::class,
        parentColumn = "authorusername",
        entityColumn = "username"
    )val userEntity: UserEntity
)
