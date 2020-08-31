package com.article.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.home.data.PersonArticleModelEntity

class TitleViewModel : ViewModel() {

    var list: MutableLiveData<MutableList<PersonArticleModelEntity>>

    init {
        list = MutableLiveData()
        list.value = mutableListOf<PersonArticleModelEntity>()

        repeat(40) {
            list.value?.add(
                PersonArticleModelEntity(
                    "سجاد فاتحی",
                    "دو روز قبل",
                    true,
                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
                )
            )
        }

    }

    fun getArticle(): MutableLiveData<MutableList<PersonArticleModelEntity>> {
        if (list == null) {
            list = MutableLiveData()

        }
        return list
    }

}