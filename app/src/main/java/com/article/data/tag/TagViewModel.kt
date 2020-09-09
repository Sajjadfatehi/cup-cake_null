package com.article.data.tag

import android.app.Application
import androidx.lifecycle.ViewModel
import com.database.tag.TagDataBase
import com.database.tag.TagDataBaseDao
import kotlinx.coroutines.*


data class TagViewModel(
    val dataBaseDao: TagDataBaseDao, val application: Application

) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //region vars
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//    var article2: LiveData<List<TagDataBase>> = MutableLiveData()
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
            insertTag(
                TagDataBase(
                    article_id = 1,
                  tag_name =   "mohammad"
                )
            )

            insertTag(
                TagDataBase(article_id =  1,
                    tag_name = "mohammad"
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

    private suspend fun updateArticle(comment: TagDataBase) {
        withContext(Dispatchers.IO) {
            dataBaseDao.updateArticle(comment)
        }
    }

    private suspend fun insertTag(comment: TagDataBase) {
        withContext(Dispatchers.IO) {
            dataBaseDao.insertArticle(comment)

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