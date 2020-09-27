package com.article.data.modelfromservice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.article.data.ArticleDataEntity

@Entity(
    tableName = "user_favorite_article",
    primaryKeys = ["username", "slug"],
    foreignKeys = [
        ForeignKey(
            entity = ArticleDataEntity::class,
            parentColumns = ["slug"],
            childColumns = ["slug"],
            onDelete = CASCADE,
            onUpdate = CASCADE

        )]
)
data class UserAndHisFavoriteArticle (
    @ColumnInfo(name = "username") val username:String,
    @ColumnInfo(name = "slug") val slug:String
)