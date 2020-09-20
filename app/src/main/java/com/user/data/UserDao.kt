package com.user.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.article.data.CommentArticleModelEntity
import com.article.data.TagModel

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

}