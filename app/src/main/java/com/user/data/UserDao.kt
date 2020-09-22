package com.user.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.article.data.ArticleUser
import com.article.data.CommentArticleModelEntity
import com.article.data.TagModel
import com.article.data.UserWithArticlesAndTagsView
import com.article.data.modelfromservice.UserAndHisFavoriteArticle

@Dao
interface UserDao {
    @Query("select * from author")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(commentArticleModelEntity: CommentArticleModelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tagModel: TagModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAndFavoriteArticle(userAndFavoriteArticles:List<UserAndHisFavoriteArticle>)

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


//    //favorite ?!
//    @Query("select * from author where username=:userName")
//    fun getUserWithArticlesAndTags(userName:String):MutableLiveData<UserWithArticlesAndTagsView>

//    @Query("SELECT * FROM article WHERE slug in(SELECT slug FROM tag_article WHERE tag =:text)")
//    fun getArticleWithTag(text: String): List<ArticleUser>

    @Query("select * from article where slug in(select slug from user_favorite_article WHERE username =:userName)")
    suspend fun getFavoriteArticlesByUser(userName: String): MutableList<ArticleUser>


    @Query("select * from article where authorusername=:userName")
    suspend fun getArticlesByAuthor(userName: String): MutableList<ArticleUser>


}
