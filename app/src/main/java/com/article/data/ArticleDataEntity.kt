package com.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by moha on 9/16/2020.
 */
@Entity(tableName = "article")
data class ArticleDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "slug") val slug: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "createdAt") val createdAt: String,
    @ColumnInfo(name = "authorusername") val aothorusername: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "favorited") val favorited: Boolean,
    @ColumnInfo(name = "favoritesCount") val favoritesCount: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "updatedAt") val updatedAt: String

)