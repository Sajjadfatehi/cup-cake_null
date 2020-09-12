package com.core

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.core.db.AppDataBase
import com.example.anull.R
import com.home.ui.PersonArticleModelView
import com.user.ui.ArticleView

class LocalDataSource {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE articles ADD COLUMN test TEXT")
        }
    }
   // private  var db: AppDataBase = AppDataBase.invoke(context,migration12 =MIGRATION_1_2 )
    var postList: MutableList<ArticleView> = mutableListOf<ArticleView>()
    var tagTitleList = mutableListOf<PersonArticleModelView>()

    init {

    }

    fun getPostInProf(): MutableList<ArticleView> {

        repeat(10) {
            postList.add(
                ArticleView(
                    R.drawable.prof_image,
                    "سجاد فاتحی",
                    "3روز پیش",
                    "کاهش درآمدهای گوگل در سهماهه دوم سال ۲۰۲۰ طی ۱۶ سال اخیر بیسابقه بوده است ",
                    "اپل در طول اعلام نتایج درآمد سه ماهه سوم خود در روز پنج شنبه گفت که هیات مدیره شرکت یک تجزیه سهام چهار به یک را تصویب کرده است",
                    "4.5k",
                    "20کامنت"
                )
            )
        }
        return postList
    }

    fun getTagList(): MutableList<PersonArticleModelView> {


        repeat(40) {
            tagTitleList.add(
                PersonArticleModelView(
                    "سجاد فاتحی",
                    "دو روز قبل",
                    true,
                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
                )
            )
        }
        return tagTitleList
    }


}