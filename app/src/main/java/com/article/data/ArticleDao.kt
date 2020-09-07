package com.article.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.user.data.ArticleAndComment
import com.user.data.ArticleAndCommentAndTag
import com.user.data.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: ArticleEntity)

    @Query("select * from articles where id=1 ")
    fun getArticle(): ArticleEntity

    @Query("select * from articles")
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Transaction
    @Query("select * from articles ")
    fun getArticleAndComment(): LiveData<List<ArticleAndComment>>


    @Transaction
    @Query("select * from articles ")
    fun getArticleAndCommentAndTag(): LiveData<List<ArticleAndCommentAndTag>>

    @Query("delete from articles")
    fun clear()

    @Update
    fun updateArticles(vararg articles: ArticleEntity)

    @Delete
    fun deleteArticles(vararg articles: ArticleEntity)

    @Query("select * from articles where id= :articleId")
    fun searchArticleWithCommentAndTag(articleId: Int): ArticleAndCommentAndTag


    /*@Query("select * from articles")
    fun getArticlesAndTags(articleEntity: ArticleEntity): ArticleAndTag

    @Query("select * from articles")
    fun getArticlesAndComments(articleEntity: ArticleEntity): ArticleAndComment*/

}