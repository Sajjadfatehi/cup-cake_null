package com.article.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ArticleWithTags (
    @Embedded val articleDataEntity: ArticleDataEntity,
    @Relation(
        parentColumn = "slug",
        entityColumn = "tag",
        associateBy = Junction(TagAndArticleEntity::class)
    )
    val tags:List<TagModel>
)