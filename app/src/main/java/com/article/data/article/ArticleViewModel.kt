package com.article.data.article

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.database.ArticleUserModel
import com.database.article.ArticleDataBase
import com.database.article.ArticleDataBaseDao
import com.part.myapplication.models.AuthRequest
import com.part.myapplication.models.User
import com.remote.AuthApi
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
    var article2: LiveData<List<ArticleUserModel>> = MutableLiveData()
    //endregion

    init {
        article2 = dataBaseDao.getAllArticleTAgAndComment()
        initialize()
    }

    fun getItemsObservable(): LiveData<List<ArticleUserModel>> {
        if (article2 == null) {
            article2 = MutableLiveData<List<ArticleUserModel>>()
        }
        return article2
    }

//    private fun searchArticle(s: CharSequence?) {
//        article2 = dataBaseDao.searchArticle(s.toString())
////        article2 = dataBaseDao.getAllArticle()
//    }

    private fun initialize() {
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

    private fun login(authApi: AuthApi) {
        GlobalScope.launch {
            val result = safeApiCall {
                authApi.login(
                    authRequest = AuthRequest(
                        User(
                            email = "test@part.ir",
                            password = "11111111"
                        )
                    )
                )
            }

            result?.let {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        application.applicationContext,
                        it.user.email,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } ?: run {

            }

        }

    }


    suspend inline fun <T> safeApiCall(responseFunction: suspend () -> T): T? {
        return try {
            responseFunction.invoke()//Or responseFunction()
        } catch (e: Exception) {
            e.printStackTrace()
            null
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