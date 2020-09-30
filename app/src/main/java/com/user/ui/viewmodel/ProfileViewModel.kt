package com.user.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.ArticleUser
import com.article.data.modelfromservice.ArticleResponse
import com.core.ResultCallBack
import com.core.util.Resource
import com.user.data.UserEntity
import com.user.data.UserRepository
import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.Article
import com.user.data.modelfromservice.FollowRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class ProfileViewModel(val userRepository: UserRepository, val userName: String) : ViewModel() {


    var isFollowing = false
    val allArticleOfPersonNew: MutableLiveData<MutableList<ArticleUser>> = MutableLiveData()
    val allFavoriteArticleOfPersonNew: MutableLiveData<MutableList<ArticleUser>> = MutableLiveData()
    val favoriteArticleResponse: MutableLiveData<ResultCallBack<ArticleResponse>> =
        MutableLiveData()
    val unFavoriteArticleResponse: MutableLiveData<ResultCallBack<ArticleResponse>> =
        MutableLiveData()
    var profile = MutableLiveData<ResultCallBack<UserEntity>>()
    var followResponse = MutableLiveData<ResultCallBack<UserEntity>>()
    var unFollowResponse = MutableLiveData<ResultCallBack<UserEntity>>()

    init {

        getProfileLocal(userName)
        getProfile(userName)
        getAllArticleOfPersonNewLocal(userName)
        getAllArticleOfPersonNew(userName)
    }

    fun getAllArticleOfPersonNew(author: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = userRepository.getAllArticleOfPersonNew(author)
        allArticleOfPersonNew.postValue(response)
    }

    fun getAllArticleOfPersonNewLocal(author: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = userRepository.getAllArticleOfPersonNewLocal(author)
        allArticleOfPersonNew.postValue(response)
    }


    fun getFavoritedArticleByUserName(favoritedUserName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.getFavoritedArticlesByUserName(favoritedUserName)
            allFavoriteArticleOfPersonNew.postValue(response)
        }

    /*  fun getFavoritedArticleByUserNameLocal(favoritedUserName: String) =
          viewModelScope.launch(Dispatchers.IO) {
              val response = userRepository.getFavoritedArticlesByUserNameLocal(favoritedUserName)
              allFavoriteArticleOfPersonNew.postValue(response)
          }*/


    private fun handleFavoritedArticleByUserName(response: Response<AllArticleOfPerson>): Resource<AllArticleOfPerson> {

        if (response.isSuccessful) {

            response.body()?.let { resultResponse ->

                if (resultResponse == null) {
                    return Resource.Success(
                        AllArticleOfPerson(
                            emptyList<Article>().toMutableList(),
                            0
                        )
                    )
                }

                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }


    fun favoriteArticle(slug: String, itemNumber: Int, ownUserName: String, isFromRadio1: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {

            favoriteArticleResponse.postValue(ResultCallBack.Loading(""))
            val response = userRepository.favoriteArticle(slug, ownUserName)

            favoriteArticleResponse.postValue(response)
            handleFavoriteArticle(response, itemNumber, isFromRadio1)

        }


    fun unFavoritedArticle(
        slug: String,
        itemNumber: Int,
        isFromRadio1: Boolean,
        ownUserName: String
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            unFavoriteArticleResponse.postValue(ResultCallBack.Loading(""))
            val response = userRepository.unFavoriteArticle(slug, ownUserName)
            unFavoriteArticleResponse.postValue(response)
            handleUnFavoriteArticle(response, itemNumber, ownUserName, isFromRadio1)

        }

    private fun handleFavoriteArticle(
        response: ResultCallBack<ArticleResponse>, itemNumber: Int, isFromRadio1: Boolean
    ) {
        if (response is ResultCallBack.Success) {
            response.data.let { articleResponse ->

                updateArticleFromList(itemNumber, articleResponse.article, isFromRadio1)
            }
        }
    }

    private fun handleUnFavoriteArticle(
        response: ResultCallBack<ArticleResponse>,
        itemNumber: Int,
        ownUserName: String,
        isFromRadio1: Boolean
    ) {
        if (response is ResultCallBack.Success) {
            response.data.let { articleResponse ->
                if (ownUserName == userName && !isFromRadio1) {
                    deleteFavoriteArticleFromListNew(itemNumber)
                } else {
                    updateArticleFromList(itemNumber, articleResponse.article, isFromRadio1)
                }

            }
        }
    }


    fun deleteArticleTest(slug: String, itemNumber: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.deleteArticle(slug)
            if (response is ResultCallBack.Success) {

                deleteArticleFromListNew(itemNumber)

            }

        }


    fun getProfile(userName: String) = viewModelScope.launch(Dispatchers.IO) {

        profile.postValue(ResultCallBack.Loading(""))
        val response = userRepository.profile(userName)
        Log.d("TAGT", "res re ${response}: ")
        profile.postValue(response)

    }

    fun getProfileLocal(userName: String) = viewModelScope.launch(Dispatchers.IO) {

        val response = userRepository.profileLocal(userName)
        Log.d("TAGT", "res lo ${response}: ")
        profile.postValue(response)
    }

    fun follow(userName: String, followRequest: FollowRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            followResponse.postValue(ResultCallBack.Loading(""))
            val response = userRepository.follow(userName, followRequest)
            followResponse.postValue(response)
        }

    fun unFollow(userName: String) = viewModelScope.launch(Dispatchers.IO) {
        unFollowResponse.postValue(ResultCallBack.Loading(""))
        val response = userRepository.unFollow(userName)
        unFollowResponse.postValue(response)
    }


    fun deleteArticleFromListNew(itemNumber: Int) {

        val tempArticle = allArticleOfPersonNew.value!![itemNumber]
        val tempList = allArticleOfPersonNew.value!!.minus(tempArticle)
        allArticleOfPersonNew.postValue(tempList.toMutableList())
    }

    fun deleteFavoriteArticleFromListNew(itemNumber: Int) {

        val tempArticle = allFavoriteArticleOfPersonNew.value!![itemNumber]
        val tempList = allFavoriteArticleOfPersonNew.value!!.minus(tempArticle)
        allFavoriteArticleOfPersonNew.postValue(tempList.toMutableList())
    }


    fun updateArticleFromList(itemNumber: Int, article: Article, isFromRadio1: Boolean) {

        if (isFromRadio1) {
            val tempList = allArticleOfPersonNew.value!!

            tempList[itemNumber] = ArticleUser(article.mapToEntity(), article.mapToUserEntity())

            allArticleOfPersonNew.postValue(tempList)
        } else {
            val tempList = allFavoriteArticleOfPersonNew.value!!

            tempList[itemNumber] = ArticleUser(article.mapToEntity(), article.mapToUserEntity())

            allFavoriteArticleOfPersonNew.postValue(tempList)

        }


    }

    fun clearDb() = userRepository.clearDb()

    fun clearSharePref() = userRepository.clearSharePref()
}