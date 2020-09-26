package com.article.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.ArticleRepository
import com.article.data.ArticleUser
import com.core.ResultCallBack
import com.home.ui.PersonArticleModelView
import kotlinx.coroutines.launch

class TitleViewModel(val articleRepository: ArticleRepository) : ViewModel() {

    var list = MutableLiveData<MutableList<PersonArticleModelView>>()


    val articlesByTag: MutableLiveData<ResultCallBack<List<ArticleUser>>> = MutableLiveData()

    var articlesByTagPage = 1
    var articlesByTagResponse: List<ArticleUser>? = null

    fun getArticlesByTag(tag: String) = viewModelScope.launch {
        val x = articlesByTagPage
        articlesByTag.postValue(ResultCallBack.Loading(""))
        val response = articleRepository.getArticleByTag(tag, articlesByTagPage)
        articlesByTag.postValue(handleArticlesByTagResponse(response))

    }

    fun handleArticlesByTagResponse(response: ResultCallBack<List<ArticleUser>>): ResultCallBack<List<ArticleUser>> {
        if (response is ResultCallBack.Success) {

            response.data.let { resultResponse ->
                articlesByTagPage++
                if (articlesByTagResponse == null) {
                    articlesByTagResponse = resultResponse
                } else {
                    val oldArticles = articlesByTagResponse
                    val newArticles = resultResponse
                    oldArticles?.toMutableList()?.addAll(newArticles)
                }

                return ResultCallBack.Success(articlesByTagResponse ?: resultResponse)

            }
        }

        return ResultCallBack.Error(Exception("error"))
    }

//
//    fun getArticleByTagNewLocal(tag:String):MutableLiveData<MutableList<ArticleUser>>{
//       return articleRepository.getArticleByTagLocal(tag)
//    }
//
//    fun getArticleByTagNewRemote(tag:String)=viewModelScope.launch(Dispatchers.IO){
//        articleRepository.getArticleByTagNewRemote(tag,articlesByTagPage )
//    }


    init {
//        list.value = articleRepository.getTagTitleList()
        getArticlesByTag("بورس")
    }

    fun getArticle(): MutableList<PersonArticleModelView>? {
//        if (list == null) {
//            list = MutableLiveData()
//

//        }
//        return list
        return list.value
    }

}