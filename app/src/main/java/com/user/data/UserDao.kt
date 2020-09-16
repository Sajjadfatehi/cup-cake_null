package com.user.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.article.data.CommentArticleModelEntity
import com.article.data.TagModel

@Dao
interface UserDao {
    @Query("select * from users")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(commentArticleModelEntity: CommentArticleModelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tagModel: TagModel)

    @Query("delete from users")
    fun clear()

    @Transaction
    @Query("select * from users")
    fun getUserWithArticleAndCommentAndTag(): LiveData<List<userWithArticleAndCommentAndTag>>

    @Update
    fun updateUsers(vararg userEntity: UserEntity)

    @Update
    fun updateComments(vararg commentArticleModelEntity: CommentArticleModelEntity)

    @Update
    fun updateTags(vararg tagModel: TagModel)

    @Delete
    fun deleteUsers(vararg userEntity: UserEntity)

    @Delete
    fun deleteComments(vararg commentArticleModelEntity: CommentArticleModelEntity)

    @Delete
    fun deleteTags(vararg tagModel: TagModel)

    @Query("select * from tags")
    fun getAllTags(): LiveData<List<TagModel>>

    @Query("select * from comments")
    fun getAllComments(): LiveData<List<CommentArticleModelEntity>>

    @Query("select * ,(select count(*) from articles where users.id=articles.articleId) as count from users")
    fun getUserAndCountOfArticle(): LiveData<List<UserAndArticleCount>>
////
//    @Query("select *, count(article.id) as count from users user join articles article on user.id = article.articleId group by user.id")
//    fun getUserAndArticleCount():LiveData<List<UserAndArticleCount>>
//
//    @Query("select *, count(articles.`desc`) from users inner join articles on users.id = articles.articleId group by users.id")
//    fun getUserAndArticleCount():List<UserAndArticleCount>

    // @Query("select ")
/*
    @Query("select * from users")
    fun getUserAndArticles(userEntity: UserEntity): UserAndArticle*/

}