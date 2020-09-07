package com.user.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val job: String,
    val image: String,
    val followers: Int,
    val writes: String

)

data class UserAndArticle(

    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "articleId"
    )
    val articleEntity: List<ArticleEntity>
)

data class userWithArticleAndCommentAndTag(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "articleId",
        entity = ArticleEntity::class
    )
    val articleAndCommentAndTag: List<ArticleAndCommentAndTag>
)

data class UserAndArticleCount(
    @Embedded val user: UserEntity,

    val count: String
)