package com.user.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.user.data.UserRepository
import com.user.ui.PostInProfView

class ProfileViewModel : ViewModel() {

    private val userRepository = UserRepository()
    var postList: MutableLiveData<MutableList<PostInProfView>> = MutableLiveData()

    init {
        postList.value = userRepository.getPostInProf()
//        postList.value = mutableListOf<PostInProfView>()
//
//        repeat(10) {
//            postList.value?.add(
//                PostInProfView(
//                    R.drawable.prof_image,
//                    "سجاد فاتحی",
//                    "3روز پیش",
//                    "کاهش درآمدهای گوگل در سهماهه دوم سال ۲۰۲۰ طی ۱۶ سال اخیر بیسابقه بوده است ",
//                    "اپل در طول اعلام نتایج درآمد سه ماهه سوم خود در روز پنج شنبه گفت که هیات مدیره شرکت یک تجزیه سهام چهار به یک را تصویب کرده است",
//                    "4.5k",
//                    "20کامنت"
//                )
//            )
//        }
    }

    fun getPosts(): MutableList<PostInProfView>? {
//        if (postList == null) {
//            postList = MutableLiveData()
//        }
//        return postList.value
        return postList.value
    }

}