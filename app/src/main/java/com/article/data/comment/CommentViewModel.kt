package com.article.data.comment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.database.comment.CommentDataBase
import com.database.comment.CommentDataBaseDao
import kotlinx.coroutines.*


data class CommentViewModel(
    val dataBaseDao: CommentDataBaseDao, val application: Application

) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //region vars
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//    var article2: LiveData<List<CommentDataBase>> = MutableLiveData()
    //endregion

    init {
//        article2 = dataBaseDao.getAllArticle()
        initializeToNight()
    }

    fun getDataSearch(s: CharSequence?) {
        searchArticle(s)
    }

    private fun searchArticle(s: CharSequence?) {
//        article2 = dataBaseDao.searchArticle(s.toString())
//        article2 = dataBaseDao.getAllArticle()
    }

    private fun initializeToNight() {
        uiScope.launch {
            insertComment(
                CommentDataBase(
                    article_id =  1,
                    text_comment = "mohammad",
                    date_comment = "22 زوط"
                )
            )

            insertComment(
                CommentDataBase(
                    article_id =  1,
                    text_comment = "mohammad",
                    date_comment = "22 زوط"
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

    private suspend fun updateArticle(articleModelEntity: CommentDataBase) {
        withContext(Dispatchers.IO) {
            dataBaseDao.updateComment(articleModelEntity)
        }
    }

    private suspend fun insertComment(articleModelEntity: CommentDataBase) {
        withContext(Dispatchers.IO) {
            dataBaseDao.insertComment(articleModelEntity)

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