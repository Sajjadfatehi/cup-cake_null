package com.article.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.ArticleRepository
import com.article.data.ArticleUser
import com.article.data.modelfromservice.ArticleResponse
import com.core.ResultCallBack
import com.home.ui.PersonArticleModelView
import com.user.data.modelfromservice.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TitleViewModel(val articleRepository: ArticleRepository, val tag: String) : ViewModel() {

    var list = MutableLiveData<MutableList<PersonArticleModelView>>()
    val articlesByTag: MutableLiveData<ResultCallBack<List<ArticleUser>>> = MutableLiveData()

    val favoriteArticleResponse: MutableLiveData<ResultCallBack<ArticleResponse>> =
        MutableLiveData()
    val unFavoriteArticleResponse: MutableLiveData<ResultCallBack<ArticleResponse>> =
        MutableLiveData()


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


    fun favoriteArticle(slug: String, itemNumber: Int, ownUserName: String) =
        viewModelScope.launch(Dispatchers.IO) {

            favoriteArticleResponse.postValue(ResultCallBack.Loading(""))
            val response = articleRepository.favoriteArticle(slug, ownUserName)

            favoriteArticleResponse.postValue(response)
            handleFavoriteArticle(response, itemNumber)

        }


    fun unFavoritedArticle(
        slug: String,
        itemNumber: Int,
        ownUserName: String
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            unFavoriteArticleResponse.postValue(ResultCallBack.Loading(""))
            val response = articleRepository.unFavoriteArticle(slug, ownUserName)
            unFavoriteArticleResponse.postValue(response)
            handleUnFavoriteArticle(response, itemNumber)

        }

    private fun handleFavoriteArticle(
        response: ResultCallBack<ArticleResponse>, itemNumber: Int
    ) {
        if (response is ResultCallBack.Success) {
            response.data.let { articleResponse ->

                updateArticleFromList(itemNumber, articleResponse.article)
            }
        }
    }

    private fun handleUnFavoriteArticle(
        response: ResultCallBack<ArticleResponse>,
        itemNumber: Int
    ) {
        if (response is ResultCallBack.Success) {
            response.data.let { articleResponse ->
                updateArticleFromList(itemNumber, articleResponse.article)

            }
        }
    }

    fun updateArticleFromList(itemNumber: Int, article: Article) {


        val tempList = articlesByTag.value!!
        if (tempList is ResultCallBack.Success) {
            val list = tempList.data.toMutableList()
            list[itemNumber].articleDataEntity.favorited = article.favorited
            articlesByTag.postValue(
                ResultCallBack.Success(
                    list
                )
            )
        }


//        articlesByTag.postValue(tempList)

    }

    init {

        getArticlesByTag(tag)
    }


}