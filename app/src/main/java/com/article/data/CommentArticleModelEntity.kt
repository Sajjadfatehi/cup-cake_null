package com.article.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
Created by Moha.Azizi on 16/08/2020 .
 */
@Entity(
    tableName = "comments", foreignKeys = [ForeignKey(
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        parentColumns = ["id"],
        childColumns = ["commentId"],
        entity = ArticleEntity::class
    )]
)
data class CommentArticleModelEntity(
    @PrimaryKey val id: Int,
    val commentId: Int,
    val name: String,
    val description: String
)