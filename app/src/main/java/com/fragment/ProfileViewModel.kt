package com.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anull.R
import com.model.PostInProf

class ProfileViewModel : ViewModel() {

    var postList: MutableLiveData<MutableList<PostInProf>> = MutableLiveData()

    init {
        postList.value = mutableListOf<PostInProf>()

        repeat(10) {
            postList.value?.add(
                PostInProf(
                    R.drawable.prof_image,
                    "سجاد فاتحی",
                    "3روز پیش",
                    "کاهش درآمدهای گوگل در سهماهه دوم سال ۲۰۲۰ طی ۱۶ سال اخیر بیسابقه بوده است ",
                    "اپل در طول اعلام نتایج درآمد سه ماهه سوم خود در روز پنج شنبه گفت که هیات مدیره شرکت یک تجزیه سهام چهار به یک را تصویب کرده است",
                    "4.5k",
                    "20کامنت"
                )
            )
        }
    }

    fun getPosts(): MutableLiveData<MutableList<PostInProf>> {
        if (postList == null) {
            postList = MutableLiveData()
        }
        return postList
    }

}