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
import com.user.ui.ArticleView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class ProfileViewModel(val userRepository: UserRepository, val userName: String) : ViewModel() {


    var isFollowing = false

    //private val userRepository = UserRepository()
    var postList: MutableLiveData<MutableList<ArticleView>> = MutableLiveData()

    // val allArticleOfPerson: MutableLiveData<Resource<AllArticleOfPerson>> = MutableLiveData()
    val allArticleOfPersonNew: MutableLiveData<MutableList<ArticleUser>> = MutableLiveData()
    val allFavoriteArticleOfPersonNew: MutableLiveData<MutableList<ArticleUser>> = MutableLiveData()
    val favoriteArticleResponse: MutableLiveData<ResultCallBack<ArticleResponse>> =
        MutableLiveData()
    val unFavoriteArticleResponse: MutableLiveData<ResultCallBack<ArticleResponse>> =
        MutableLiveData()
    var allArticleOfPersonPage = 1
    var allArticleOfPersonResponse: AllArticleOfPerson? = null

    var profile = MutableLiveData<ResultCallBack<UserEntity>>()

    var followResponse = MutableLiveData<ResultCallBack<UserEntity>>()
    var unFollowResponse = MutableLiveData<ResultCallBack<UserEntity>>()

//    fun getAllArticleOfPerson(author: String) = viewModelScope.launch {
//        allArticleOfPerson.postValue(Resource.Loading())
//
//        val response = userRepository.getAllArticleOfPerson(author, allArticleOfPersonPage)
//        allArticleOfPerson.postValue(handleAllArticleOfPersonResponse(response))
//    }


    init {
        getProfileLocal(userName)
        // getProfile(userName)
//
//        getAllArticleOfPersonNewLocal(userName)
        getAllArticleOfPersonNew(userName)


    }

    fun getAllArticleOfPersonNew(author: String) = viewModelScope.launch(Dispatchers.IO) {
        //allArticleOfPersonNew.postValue(Resource.Loading())

        val response = userRepository.getAllArticleOfPersonNew(author, allArticleOfPersonPage)

        allArticleOfPersonNew.postValue(response)
    }

    fun getAllArticleOfPersonNewLocal(author: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = userRepository.getAllArticleOfPersonNewLocal(author)
        allArticleOfPersonNew.postValue(response)
    }

    private fun handleAllArticleOfPersonResponse(response: Response<AllArticleOfPerson>): Resource<AllArticleOfPerson> {
        if (response.isSuccessful) {

            response.body()?.let { resultResponse ->
//                allArticleOfPersonPage++
//                if (allArticleOfPersonResponse == null) {
//                    allArticleOfPersonResponse = resultResponse
//                } else {
//                    val oldArticles = allArticleOfPersonResponse?.articles
//                    val newArticles = resultResponse.articles
//                    oldArticles?.addAll(newArticles)
//                }

                if (resultResponse == null) {
                    return Resource.Success(
                        AllArticleOfPerson(
                            emptyList<Article>().toMutableList(),
                            0
                        )
                    )
                }
                return Resource.Success(allArticleOfPersonResponse ?: resultResponse)

            }
        }

        return Resource.Error(response.message())
    }


    fun getFavoritedArticleByUserName(favoritedUserName: String) =
        viewModelScope.launch(Dispatchers.IO) {
//            allFavoriteArticleOfPersonNew.postValue(ResultCallBack.Loading(""))
            val response = userRepository.getFavoritedArticlesByUserName(favoritedUserName)
            allFavoriteArticleOfPersonNew.postValue(response)
        }

    fun getFavoritedArticleByUserNameLocal(favoritedUserName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.getFavoritedArticlesByUserNameLocal(favoritedUserName)
            allFavoriteArticleOfPersonNew.postValue(response)
        }


    private fun handleFavoritedArticleByUserName(response: Response<AllArticleOfPerson>): Resource<AllArticleOfPerson> {

        if (response.isSuccessful) {
            Log.d("tokhmi", "bb: ${response.body()?.articlesCount} ")

            response.body()?.let { resultResponse ->
                // allArticleOfPersonPage++
//                if (allArticleOfPersonResponse == null) {
//                    allArticleOfPersonResponse = resultResponse
//                } else {
//                    val oldArticles = allArticleOfPersonResponse?.articles
//                    val newArticles = resultResponse.articles
//                    oldArticles?.addAll(newArticles)
//                }
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


    fun favoriteArticle(slug: String, itemNumber: Int, ownUserName: String) =
        viewModelScope.launch(Dispatchers.IO) {

            favoriteArticleResponse.postValue(ResultCallBack.Loading(""))
            val response = userRepository.favoriteArticle(slug, ownUserName)

            favoriteArticleResponse.postValue(response)
            handleFavoriteArticle(response, itemNumber)

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
//                unFavoriteArticleResponse.postValue(response)
//                if (response is ResultCallBack.Success) {
//                    response.data.let { articleResponse ->
//                        //   updateArticleFromList(itemNumber, articleResponse.article)
//
//                    }
//                }
            handleUnFavoriteArticle(response, itemNumber, ownUserName, isFromRadio1)

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
        itemNumber: Int,
        ownUserName: String,
        isFromRadio1: Boolean
    ) {
        if (response is ResultCallBack.Success) {
            response.data.let { articleResponse ->
                if (ownUserName == userName && !isFromRadio1) {
                    deleteArticleFromListNew(itemNumber)
                } else {
                    updateArticleFromList(itemNumber, articleResponse.article)
                }

            }
        }
    }


    fun deleteArticleTest(slug: String, itemNumber: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.deleteArticle(slug)
            if (response is ResultCallBack.Success) {

                Log.d("mamad", "yes deket in view model sucees: ")
                deleteArticleFromListNew(itemNumber)

            }

        }


    fun getProfile(userName: String) = viewModelScope.launch(Dispatchers.IO) {

        profile.postValue(ResultCallBack.Loading(""))
        val response = userRepository.profile(userName)
        profile.postValue(response)

    }

    fun getProfileLocal(userName: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = userRepository.profileLocal(userName)
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



    fun getPosts(): MutableList<ArticleView>? {

//        if (postList == null) {
//            postList = MutableLiveData()
//        }
//        return postList.value
        return postList.value
    }
//
//    fun deleteArticleFromList(itemNumber: Int) {
//
//        val tempArticle = allArticleOfPerson.value?.data?.articles!![itemNumber]
//        val tempList = allArticleOfPerson.value!!.data?.articles!!.minus(tempArticle)
//        val artciNew = AllArticleOfPerson(tempList.toMutableList(), tempList.size)
//        allArticleOfPerson.postValue(Resource.Success(artciNew))
//    }

    fun deleteArticleFromListNew(itemNumber: Int) {

        val tempArticle = allArticleOfPersonNew.value!![itemNumber]
        val tempList = allArticleOfPersonNew.value!!.minus(tempArticle)
        allArticleOfPersonNew.postValue(tempList.toMutableList())
    }


    fun updateArticleFromList(itemNumber: Int, article: Article) {

        // val tempArticle = allArticleOfPersonNew.value!![itemNumber]
        val tempList = allArticleOfPersonNew.value!!

        tempList[itemNumber] = ArticleUser(article.mapToEntity(), article.mapToUserEntity())

        allArticleOfPersonNew.postValue(tempList)
    }


}