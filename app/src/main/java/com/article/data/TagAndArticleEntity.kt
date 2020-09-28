package com.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

/**
 * Created by moha on 9/16/2020.
 */

@Entity(
    tableName = "tag_article",
    primaryKeys = ["tag", "slug"],
    foreignKeys = [
        ForeignKey(
            entity = ArticleDataEntity::class,
            parentColumns = ["slug"],
            childColumns = ["slug"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )]
)
data class TagAndArticleEntity(
    @ColumnInfo(name = "tag") val tag: String,
    @ColumnInfo(name = "slug") val article: String
)