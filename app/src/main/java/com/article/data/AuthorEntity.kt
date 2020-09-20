package com.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by moha on 9/16/2020.
 */
@Entity(tableName = "author")
data class AuthorEntity(
    @PrimaryKey
    @ColumnInfo(name = "username") val username : String,
    @ColumnInfo(name = "bio")val bio: String,
    @ColumnInfo(name = "following") val following: Boolean,
    @ColumnInfo(name = "image")val image: String
)