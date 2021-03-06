package com.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.article.data.*
import com.article.data.modelfromservice.UserAndHisFavoriteArticle
import com.user.data.UserDao
import com.user.data.UserEntity

@Database(
    entities = [UserEntity::class, ArticleDataEntity::class, TagModel::class,
        CommentArticleModelEntity::class, TagAndArticleEntity::class,UserAndHisFavoriteArticle::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase() : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    abstract fun tagDao(): TagDao

    abstract fun userDao(): UserDao



    companion object {

        @Volatile
        private var instance:AppDataBase?=null
        private val LOCK=Any()

        operator fun invoke(context: Context, migration12: Migration)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context,migration12).also { instance=it }
        }
        private const val databaseName = "data_base"

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