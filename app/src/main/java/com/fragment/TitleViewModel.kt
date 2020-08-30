package com.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.model.home.PersonArticleModel

class TitleViewModel : ViewModel() {

    var list: MutableLiveData<MutableList<PersonArticleModel>>

    init {
        list = MutableLiveData()
        list.value = mutableListOf<PersonArticleModel>()

        repeat(40) {
            list.value?.add(
                PersonArticleModel(
                    "سجاد فاتحی",
                    "دو روز قبل",
                    true,
                    " ین متن میتواند یک تست موقت باشد ین متن میتواند یک تست موقت باشد"
                )
            )
        }

    }

    fun getArticle(): MutableLiveData<MutableList<PersonArticleModel>> {
        if (list == null) {
            list = MutableLiveData()

        }
        return list
    }

}