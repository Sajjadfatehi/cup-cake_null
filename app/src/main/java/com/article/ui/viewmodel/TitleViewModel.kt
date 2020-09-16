package com.article.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.article.data.ArticleRepository
import com.home.ui.PersonArticleModelView

class TitleViewModel : ViewModel() {
    private val articleRepository = ArticleRepository()
    var list = MutableLiveData<MutableList<PersonArticleModelView>>()

    init {
//        list.value = articleRepository.getTagTitleList()


    }

    fun getArticle(): MutableList<PersonArticleModelView>? {
//        if (list == null) {
//            list = MutableLiveData()
//

//        }
//        return list
        return list.value
    }

}