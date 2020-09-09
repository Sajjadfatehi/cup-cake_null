package com.database.tag

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagDataBaseDao {
    @Insert
    fun insertArticle(article: TagDataBase)

    @Delete
    fun deleteArticle(vararg article: TagDataBase)

    @Update
    fun updateArticle(vararg article: TagDataBase)

    @Query("SELECT * FROM tag_table")
    fun getAllArticle(): LiveData<List<TagDataBase>>

    @Query("SELECT * FROM tag_table WHERE tag_name LIKE :name")
    fun searchArticle(name: String): LiveData<List<TagDataBase>>

//    @Query(
//        "select DISTINCT a.* " +
//                "from articles as a " +
//                "join keywords_and_articles as ka on ka.article_id = a.article_id " +
//                "join keywords as k on k.keyword_id = ka.keyword_id " +
//                "join comments as c on c.article_id = a.article_id " +
//                "where c.content like :word or k.title like :word or a.content like :word or a.title like :word "
//    )
//    fun searchArticle(word: String): LiveData<MutableList<ArticleDataModel>>
//
//    data class ArticleDataModel(
//        @Embedded val article: ArticleEntity,
//        @Relation(
//            entity = UserEntity::class,
//            parentColumn = "owner_id",
//            entityColumn = "user_id"
//        ) val user: UserEntity,
//        @Relation(
//            entity = CommentEntity::class,
//            parentColumn = "article_id",
//            entityColumn = "article_id"
//        ) val comments: MutableList<CommentEntity>,
//        @Relation(
//            entity = LikeEntity::class,
//            parentColumn = "article_id",
//            entityColumn = "article_id"
//        ) val likes: MutableList<LikeEntity>,
//        @Relation(
//            entity = KeywordsAndArticles::class,
//            parentColumn = "article_id",
//            entityColumn = "article_id"
//        )
//        val keywords: MutableList<KeywordsAndArticles>
//
//        @Query(
//    "SELECT users.*, x.num " +
//    "from users ,( " +
//    "SELECT owner_id as owner, COUNT(*) as num " +
//    "from articles join users on user_id = owner_id " +
//    "GROUP BY user_id) as x" +
//    " WHERE user_id = x.owner"
//    )
//    fun getUserWithArticleCount(): LiveData<MutableList<UserAndArticlesNumber>>


//    @Query("SELECT * FROM article_data_base WHERE articleId = :articleId ")
//    fun getAllArticle(articleId: Long): LiveData<List<PersonArticleModelEntity>>
/*
    @Query("DELETE FROM  article_data_base")
    fun deleteAll()*/
}