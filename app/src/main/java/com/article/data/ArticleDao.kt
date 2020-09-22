package com.article.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<ArticleDataEntity>)

    @Update
    suspend fun update(article:ArticleDataEntity)

    @Query("SELECT * FROM article WHERE slug in(SELECT slug FROM tag_article WHERE tag =:text)")
    fun getArticleWithTag(text: String): List<ArticleUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleTag(tagAndArticleEntity: List<TagAndArticleEntity>)

    @Query("delete from article where slug=:slug;")
    suspend fun deleteArticle(slug: String)

    @Query("select * from article")
    suspend fun getAllArticles():MutableList<ArticleDataEntity>
}