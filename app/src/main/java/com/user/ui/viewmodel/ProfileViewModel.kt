package com.user.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource

import com.user.data.UserRepository
import com.user.data.modelfromservice.AllArticleOfPerson
import com.user.data.modelfromservice.Author
import com.user.ui.ArticleView
import kotlinx.coroutines.launch
import retrofit2.Response


class ProfileViewModel(val userRepository: UserRepository) : ViewModel() {

    //private val userRepository = UserRepository()
    var postList: MutableLiveData<MutableList<ArticleView>> = MutableLiveData()
    val allArticleOfPerson: MutableLiveData<com.core.util.Resource<AllArticleOfPerson>> =
        MutableLiveData()
    var allArticleOfPersonPage = 1
    var allArticleOfPersonResponse: AllArticleOfPerson? = null

    fun getAllArticleOfPerson(author: String) = viewModelScope.launch {
        allArticleOfPerson.postValue(Resource.Loading())
        val response = userRepository.getAllArticleOfPerson(author, allArticleOfPersonPage)
        allArticleOfPerson.postValue(handleAllArticleOfPersonResponse(response))

    }

    fun handleAllArticleOfPersonResponse(response: Response<AllArticleOfPerson>): Resource<AllArticleOfPerson> {
        if (response.isSuccessful) {
//            Log.d("lashii", "${response.body()?.articles?.get(0)?.slug} ")
            response.body()?.let { resultResponse ->
                allArticleOfPersonPage++
                if (allArticleOfPersonResponse == null) {
                    allArticleOfPersonResponse = resultResponse
                } else {
                    val oldArticles = allArticleOfPersonResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success(allArticleOfPersonResponse ?: resultResponse)

            }
        }

        return Resource.Error(response.message())
    }


    init {

        postList.value = userRepository.getPostInProf()

        getAllArticleOfPerson("ali1748")
    }

    fun getPosts(): MutableList<ArticleView>? {

//        if (postList == null) {
//            postList = MutableLiveData()
//        }
//        return postList.value
        return postList.value
    }

}