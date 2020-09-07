package com.article.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.user.data.ArticleEntity

@Entity(
    tableName = "tags", foreignKeys = [ForeignKey(
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        parentColumns = ["id"],
        childColumns = ["tagId"],
        entity = ArticleEntity::class
    )]
)
data class TagEntity(
    @PrimaryKey val id: Int,
    var tagId: Long,
    var text: String
)
