package com.article.ui.viewmodel

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip
import com.user.ui.ArticleView


class WriteArticleViewModel : ViewModel() {


    var activity: FragmentActivity = FragmentActivity()
    val tagsChip = mutableMapOf<Chip, String>()
    var argsFromProf: Bundle = Bundle()
    var isFromEdit = MutableLiveData<Boolean>()
    lateinit var article: ArticleView


    fun checkArgsIsNull(bundle: Bundle?) {

        if (bundle != null) {
            argsFromProf = bundle
        }
        if (!argsFromProf.isEmpty) {
            article = argsFromProf.getParcelable<ArticleView>("post")!!
        }
        isFromEdit.value = bundle != null

    }


}



