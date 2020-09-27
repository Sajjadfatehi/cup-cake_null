package com.article.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.ArticleRepository
import com.article.data.ArticleUser
import com.core.ResultCallBack
import com.home.ui.PersonArticleModelView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TitleViewModel(val articleRepository: ArticleRepository) : ViewModel() {

    var list = MutableLiveData<MutableList<PersonArticleModelView>>()


    val articlesByTag: MutableLiveData<ResultCallBack<List<ArticleUser>>> = MutableLiveData()


    var articlesByTagResponse: List<ArticleUser>? = null

    fun getArticlesByTag(tag: String) = viewModelScope.launch(Dispatchers.IO) {

        articlesByTag.postValue(ResultCallBack.Loading(""))
        val response = articleRepository.getArticleByTag(tag)
        articlesByTag.postValue(handleArticlesByTagResponse(response))

    }

    fun handleArticlesByTagResponse(response: ResultCallBack<List<ArticleUser>>): ResultCallBack<List<ArticleUser>> {
        if (response is ResultCallBack.Success) {
            response.data.let { resultResponse ->
                return ResultCallBack.Success(resultResponse)
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