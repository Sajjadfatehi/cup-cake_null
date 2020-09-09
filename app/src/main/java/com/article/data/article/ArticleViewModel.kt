package com.article.data.article

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.database.article.ArticleDataBase
import com.database.article.ArticleDataBaseDao
import kotlinx.coroutines.*


data class ArticleViewModel(
    val dataBaseDao: ArticleDataBaseDao, val application: Application

) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //region vars
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var article2: LiveData<List<ArticleDataBase>> = MutableLiveData()
    //endregion

    init {
        article2 = dataBaseDao.getAllArticle()
        initializeToNight()
    }

    fun getItemsObservable(): LiveData<List<ArticleDataBase>> {
        if (article2 == null) {
            article2 = MutableLiveData<List<ArticleDataBase>>()
        }
        return article2
    }

    fun getDataSearch(s: CharSequence?) {
        searchArticle(s)
    }

    private fun searchArticle(s: CharSequence?) {
        article2 = dataBaseDao.searchArticle(s.toString())
//        article2 = dataBaseDao.getAllArticle()
    }

    private fun initializeToNight() {
        uiScope.launch {
            insertArticle(
                ArticleDataBase(
                    name_article = "mohammad",
                    description_article = "کاهش درامد های گوگل در سال 2020 در شانزده سال اخیر بی سابقه بوده ات",
                    favorite_article = false,
                    date_article = "5 روز پیش"
                )
            )
        }
    }

//    private suspend fun getArticleFromDataBase(): LiveData<List<RelatedArticleModelEntity>?> {
//        return withContext(Dispatchers.IO) {
//            var article = dataBaseDao.getAllArticle()
//            article
//        }
//    }

    private suspend fun updateArticle(articleModelEntity: ArticleDataBase) {
        withContext(Dispatchers.IO) {
            dataBaseDao.updateArticle(articleModelEntity)
        }
    }

    private suspend fun insertArticle(article: ArticleDataBase) {
        withContext(Dispatchers.IO) {
            dataBaseDao.insertArticle(article)

        }
    }

//    private suspend fun deleteArticle(articleModelEntity: RelatedArticleModelEntity) {
//        withContext(Dispatchers.IO) {
//            dataBaseDao.deleteArticle(articleModelEntity)
//        }
}

//    private suspend fun deleteAllArticle() {
////        withContext(Dispatchers.IO) {
////            dataBaseDao.deleteAll()
////        }
//    }
//}