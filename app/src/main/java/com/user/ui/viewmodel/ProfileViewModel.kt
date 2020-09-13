package com.user.ui.viewmodel

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
    val allArticleOfPersonPage = 1

    fun getAllArticleOfPerson(author: String) = viewModelScope.launch {
        allArticleOfPerson.postValue(com.core.util.Resource.Loading())
        val response= userRepository.getAllArticleOfPerson(author,allArticleOfPersonPage)
        allArticleOfPerson.postValue(handleAllArticleOfPersonResponse(response))

    }

    fun handleAllArticleOfPersonResponse(response: Response<AllArticleOfPerson>):Resource<AllArticleOfPerson>{
        if (response.isSuccessful){
            response.body()?.let {resultResponse->
                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }


    init {

        postList.value = userRepository.getPostInProf()

       // getAllArticleOfPerson("ali1748")
    }

    fun getPosts(): MutableList<ArticleView>? {

//        if (postList == null) {
//            postList = MutableLiveData()
//        }
//        return postList.value
        return postList.value
    }

}