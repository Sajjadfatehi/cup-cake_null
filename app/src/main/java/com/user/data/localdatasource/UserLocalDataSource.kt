package com.user.data.localdatasource

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.article.data.ArticleDataEntity
import com.article.data.TagAndArticleEntity
import com.article.data.TagModel
import com.article.data.modelfromservice.UserAndHisFavoriteArticle
import com.core.db.AppDataBase
import com.user.data.UserEntity

class UserLocalDataSource(val db:AppDataBase) {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE articles ADD COLUMN test TEXT")
        }
    }

    fun addUsers(userEntity: List<UserEntity>) {
        db.userDao().insertUser(userEntity)
    }


    fun addArticleTag(tagAndArticleEntity: List<TagAndArticleEntity>) {
        db.articleDao().insertArticleTag(tagAndArticleEntity)
    }

    suspend fun addUserAndFavoriteArticles(userAdFavoriteArticles: List<UserAndHisFavoriteArticle>) {
        db.userDao().insertUserAndFavoriteArticle(userAdFavoriteArticles)

    }


    suspend fun insertTags(tags: List<TagModel>) {
        db.tagDao().insertTags(tags)
    }

    suspend fun getUser(userName: String): UserEntity {
        return db.userDao().getUser(userName)
    }


    suspend fun addTags(tags: List<TagModel>) {
        db.tagDao().addTAg(tags)
    }


    fun addArticle(articles: List<ArticleDataEntity>) {
        db.articleDao().insert(articles)
    }
    suspend fun deleteArticle(slug:String){
        db.articleDao().deleteArticle(slug)
    }

}