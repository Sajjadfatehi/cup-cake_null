package com.database.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.database.ArticleUserModel


/**
 * Created by moha on 9/6/2020.
 */

@Dao
interface UserDataBaseDao {

    @Insert
    fun insertUser(vararg user: UserDataBase)

    @Delete
    fun deleteUser(vararg user: UserDataBase)

    @Update
    fun updateUser(vararg user: UserDataBase)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<UserDataBase>>


//    @Query(
//        "select DISTINCT a.* " +
//                "from articles as a " +
//                "join keywords_and_articles as ka on ka.article_id = a.article_id " +
//                "join keywords as k on k.keyword_id = ka.keyword_id " +
//                "join comments as c on c.article_id = a.article_id " +
//                "where c.content like :word or k.title like :word or a.content like :word or a.title like :word "
//    )
//    fun getArticleUser(): LiveData<MutableList<ArticleUserModel>>

}