package com.article.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "comments", foreignKeys = [ForeignKey(
        onDelete = CASCADE,
        onUpdate = CASCADE,
        parentColumns = ["slug"],
        childColumns = ["article_slug"],
        entity = ArticleDataEntity::class
    )]
)
data class CommentArticleModelEntity(
    @PrimaryKey val id: Int,
    val article_slug: Int,
    val name: String,
    val description: String
)