package com.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.database.article.ArticleDataBase
import com.database.article.ArticleDataBaseDao
import com.database.comment.CommentDataBase
import com.database.comment.CommentDataBaseDao
import com.database.tag.TagDataBase
import com.database.tag.TagDataBaseDao
import com.database.user.UserDataBase
import com.database.user.UserDataBaseDao

@Database(
    entities = [ArticleDataBase::class, CommentDataBase::class, TagDataBase::class,
        UserDataBase::class], version = 1, exportSchema = false
)
abstract class DataBase : RoomDatabase() {

    abstract val articleDataBaseDao: ArticleDataBaseDao
    abstract val userDataBaseDao: UserDataBaseDao
    abstract val commentDataBaseDao: CommentDataBaseDao
    abstract val tagDataBaseDao: TagDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            synchronized(this) {
                var instance: DataBase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "null_database"
                    )
                        .build()
                }
                return instance
            }
        }
    }

}
