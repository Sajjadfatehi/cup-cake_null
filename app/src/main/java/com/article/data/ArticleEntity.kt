package com.article.data

import android.os.Parcelable
import androidx.room.*
import com.user.data.UserEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(
    tableName = "articles", foreignKeys = [ForeignKey(
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        parentColumns = ["id"],
        childColumns = ["articleId"],
        entity = UserEntity::class
    )]
)
data class ArticleEntity(


    @PrimaryKey val id: Int,

    val articleId: Int,
    val lastDate: Date,
    val title: String,
    val desc: String,
    val tvLike: String,
    val tvComment: String,
    val bookMark: Boolean
) : Parcelable


data class ArticleAndCommentAndTag(

    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "commentId"

    )
    val commentArticleModelEntity: List<CommentArticleModelEntity>

//    @Relation(
//        parentColumn = "id",
//        entityColumn = "tagId"
//    )
//    val tagModel: List<TagModel>
)
