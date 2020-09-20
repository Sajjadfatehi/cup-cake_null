package com.article.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<ArticleDataEntity>)

    @Query("SELECT * FROM article WHERE slug in(SELECT slug FROM tag_article WHERE tag =:text)")
    fun getArticleWithTag(text: String): List<ArticleUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleTag(tagAndArticleEntity: List<TagAndArticleEntity>)
}