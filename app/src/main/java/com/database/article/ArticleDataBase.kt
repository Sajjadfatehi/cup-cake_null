package com.database.article

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")//add user foreignKeys
data class ArticleDataBase(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "article_id") var articleId: Long = 0L,

    @ColumnInfo(name = "name_article")
    var name_article: String?,

    @ColumnInfo(name = "description")
    var description_article: String?,

    @ColumnInfo(name = "favorite_article")
    var favorite_article: Boolean?,

    @ColumnInfo(name = "date_article")
    var date_article: String?
)