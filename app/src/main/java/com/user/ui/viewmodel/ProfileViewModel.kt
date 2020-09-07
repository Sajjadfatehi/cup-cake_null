package com.user.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.user.data.UserRepository
import com.user.ui.ArticleView

class ProfileViewModel : ViewModel() {

    private val userRepository = UserRepository()
    var postList: MutableLiveData<MutableList<ArticleView>> = MutableLiveData()

    init {
        postList.value = userRepository.getPostInProf()

    }

    fun getPosts(): MutableList<ArticleView>? {
//        if (postList == null) {
//            postList = MutableLiveData()
//        }
//        return postList.value
        return postList.value
    }

}