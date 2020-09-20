package com.article.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.user.data.UserEntity

/**
 * Created by moha on 9/16/2020.
 */
@Entity(tableName = "article_author")
data class ArticleAuthor(
    @Embedded val   articleDataEntity: ArticleDataEntity,
    @Relation(
        entity = UserEntity::class,
        parentColumn = "username",
        entityColumn = "authorusername"

    )val user :UserEntity
)