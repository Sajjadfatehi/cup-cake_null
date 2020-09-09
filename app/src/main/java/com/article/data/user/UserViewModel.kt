package com.article.data.user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.database.article.ArticleDataBase
import com.database.article.ArticleDataBaseDao
import com.database.user.UserDataBase
import com.database.user.UserDataBaseDao
import kotlinx.coroutines.*


data class UserViewModel(
    val dataBaseDao: UserDataBaseDao, val application: Application

) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //region vars
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//    var article2: LiveData<List<ArticleDataBase>> = MutableLiveData()
    //endregion

    init {
//        article2 = dataBaseDao.getAllArticle()
        initializeToNight()
    }


    private fun initializeToNight() {
        uiScope.launch {
            insertArticle(
                UserDataBase(
                    0, 0,"mohammad", "azizi", "0098"
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

    private suspend fun updateArticle(user: UserDataBase) {
        withContext(Dispatchers.IO) {
            dataBaseDao.updateUser(user)
        }
    }

    private suspend fun insertArticle(user: UserDataBase) {
        withContext(Dispatchers.IO) {
            dataBaseDao.insertUser(user)

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