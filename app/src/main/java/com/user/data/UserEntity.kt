package com.user.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "following") val following: Boolean,
    @ColumnInfo(name = "bio") val bio: String? = ""

)
//
//data class UserAndArticle(
//
//    @Embedded val userEntity: UserEntity,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "articleId"
//    )
//    val articleEntity: List<ArticleDataModel>
//)
//
//data class userWithArticleAndCommentAndTag(
//    @Embedded val user: UserEntity,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "articleId",
//        entity = ArticleDataEntity::class
//    )
//    val articleAndCommentAndTag: List<ArticleAndCommentAndTag>
//)
//
//data class UserAndArticleCount(
//    @Embedded val user: UserEntity,
//
//    val count: String
//)