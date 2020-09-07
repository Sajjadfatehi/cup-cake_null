package com.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.article.data.ArticleDao
import com.article.data.CommentArticleModelEntity
import com.article.data.TagEntity
import com.user.data.ArticleEntity
import com.user.data.UserDao
import com.user.data.UserEntity

@Database(
    entities = [UserEntity::class, ArticleEntity::class, TagEntity::class, CommentArticleModelEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun userDao(): UserDao

    companion object {

        private const val databaseName = "null-db"

        fun buildDatabase(
            context: Context,
            migration12: Migration
        ): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addMigrations(migration12)
                .build()
        }
    }

}