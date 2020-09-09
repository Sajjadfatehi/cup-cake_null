package com.database.comment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.database.article.ArticleDataBase

@Entity(
    tableName = "comment_table",
    foreignKeys = [ForeignKey(
        entity = ArticleDataBase::class,
        parentColumns = ["article_id"],
        childColumns = ["to_article"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CommentDataBase(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "comment_id") var commentId: Long = 0L,

    @ColumnInfo(name = "to_article")
    var article_id: Long,

    @ColumnInfo(name = "text_comment")
    var text_comment: String?,

    @ColumnInfo(name = "date_comment")
    var date_comment: String?


)