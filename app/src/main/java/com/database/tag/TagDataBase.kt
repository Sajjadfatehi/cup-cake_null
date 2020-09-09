package com.database.tag

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.database.article.ArticleDataBase

/**
 * Created by moha on 9/7/2020.
 */
@Entity(
    tableName = "tag_table",
    foreignKeys = [ForeignKey(
        entity = ArticleDataBase::class,
        parentColumns = ["article_id"],
        childColumns = ["to_article"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TagDataBase(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "tag_id") var tagId: Long = 0L,

    @ColumnInfo(name = "to_article")
    var article_id: Long,

    @ColumnInfo(name = "tag_name")
    var tag_name: String?
)