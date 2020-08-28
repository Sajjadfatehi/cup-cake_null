package com.fragment

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip
import com.model.PostInProf


class WriteArticleViewModel : ViewModel() {


    var argsFromProf: Bundle = Bundle()
    var isFromEdit = MutableLiveData<Boolean>()
    val tagsChip = MutableLiveData<MutableMap<Chip, String>>()
    lateinit var postInProf: PostInProf


    fun checkArgsIsNull(bundle: Bundle?) {
        if (bundle != null) {
            argsFromProf = bundle
        }
        if (!argsFromProf.isEmpty) {
            postInProf = argsFromProf.getParcelable<PostInProf>("post")!!
        }
        isFromEdit.value = bundle != null


    }


}