package com.user.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import com.user.data.UserRepository
import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.Article
import com.user.ui.ArticleView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class ProfileViewModel(val userRepository: UserRepository, val userName: String) : ViewModel() {

    //private val userRepository = UserRepository()
    var postList: MutableLiveData<MutableList<ArticleView>> = MutableLiveData()
    val allArticleOfPerson: MutableLiveData<Resource<AllArticleOfPerson>> = MutableLiveData()
    val favoriteArticleResponse: MutableLiveData<Resource<Article>> = MutableLiveData()
    var allArticleOfPersonPage = 1
    var allArticleOfPersonResponse: AllArticleOfPerson? = null

    var isDeleteSuccess = MutableLiveData<Boolean>()
    var itemNumberOfDeletedArticle = -1



     fun getAllArticleOfPerson(author: String) = viewModelScope.launch {
         allArticleOfPerson.postValue(Resource.Loading())

         val response = userRepository.getAllArticleOfPerson(author, allArticleOfPersonPage)
         allArticleOfPerson.postValue(handleAllArticleOfPersonResponse(response))
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
            allArticleOfPerson.postValue(Resource.Loading())
            val response = userRepository.favoritedArticleByUserName(favoritedUserName)
            allArticleOfPerson.postValue(handleFavoritedArticleByUserName(response))
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


    fun favoriteArticle(slug: String) = viewModelScope.launch(Dispatchers.IO) {
        favoriteArticleResponse.postValue(Resource.Loading())
        val response = userRepository.favoriteArticle(slug)
        favoriteArticleResponse.postValue(handleFavoriteArticle(response))

    }

    private fun handleFavoriteArticle(response: Response<Article>): Resource<Article> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun deleteArticleTest(slug: String, itemNumber: Int) = viewModelScope.launch {
        allArticleOfPerson.postValue(Resource.Loading())
        val response = userRepository.deleteArticle(slug)
        handleDeleteArticleTest(response, itemNumber)
        if (response.isSuccessful) {
            itemNumberOfDeletedArticle = itemNumber
            isDeleteSuccess.postValue(true)

        }
    }

    private fun handleDeleteArticleTest(response: Response<Unit>, itemNumber: Int): Resource<Unit> {
        if (response.isSuccessful) {

            return Resource.Success(Unit)
        }
        return Resource.Error(response.message())
    }


    init {

        postList.value = userRepository.getPostInProf()

        getAllArticleOfPerson(userName)


    }

    fun getPosts(): MutableList<ArticleView>? {

//        if (postList == null) {
//            postList = MutableLiveData()
//        }
//        return postList.value
        return postList.value
    }

    fun deleteArticleFromList(itemNumber: Int) {
        val art = allArticleOfPerson.value?.data?.articles!![itemNumber]
        val re = allArticleOfPerson.value!!.data?.articles!!.minus(art)
        val artciNew = AllArticleOfPerson(re.toMutableList(), re.size)
        allArticleOfPerson.postValue(Resource.Success(artciNew))
    }

}