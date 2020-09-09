package com.database

import androidx.room.Embedded
import androidx.room.Relation
import com.database.article.ArticleDataBase
import com.database.comment.CommentDataBase
import com.database.tag.TagDataBase
import com.database.user.UserDataBase

/**
 * Created by moha on 9/7/2020.
 */
data class ArticleUserModel(
    @Embedded val article: ArticleDataBase,
    @Relation(
        parentColumn = "article_id",
        entityColumn = "to_article"
    ) val user: List<UserDataBase>,
    @Relation(
        parentColumn = "article_id",
        entityColumn = "to_article"
    ) val comment: List<CommentDataBase>

)