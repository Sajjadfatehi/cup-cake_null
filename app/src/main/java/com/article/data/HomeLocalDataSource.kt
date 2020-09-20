package com.article.data

import com.article.data.TagModel
import com.core.RoomDataBase
import com.example.anull.R
import com.home.ui.PersonArticleModelView
import com.user.data.UserEntity
import com.user.ui.ArticleView

class HomeLocalDataSource() {
    var postList: MutableList<ArticleView> = mutableListOf<ArticleView>()
    var tagTitleList = mutableListOf<PersonArticleModelView>()
    lateinit var tags: List<TagModel>
    lateinit var articles: List<ArticleUser>
    private val dataBase = RoomDataBase.getDB()
    private val dao = dataBase.tagDao()
    private val userDao = dataBase.userDao()
    private val articleDao = dataBase.articleDao()


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

    fun getTagList(): List<TagModel> {
        tags = dao.getAllTag()
        return tags
    }

    fun getArticleWithTag(text: String): List<ArticleUser> {
        articles = articleDao.getArticleWithTag(text)
        return articles
    }

    fun addAllTags(tags: List<TagModel>) {
        dao.addTAg(tags)
    }

    fun addArticle(articles: List<ArticleDataEntity>) {
        articleDao.insert(articles)
    }

    fun addArticleTag(tagAndArticleEntity: List<TagAndArticleEntity>) {
        articleDao.insertArticleTag(tagAndArticleEntity)
    }

    fun addUsers(userEntity: List<UserEntity>) {
        userDao.insertUser(userEntity)
    }

}