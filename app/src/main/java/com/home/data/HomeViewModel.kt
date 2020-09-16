package com.home.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.ArticleRepository
import com.article.data.TagModel
import com.core.ResultCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by moha on 9/15/2020.
 */
class HomeViewModel(val repo : ArticleRepository):ViewModel() {

    val tags = MutableLiveData<List<TagModel>>()

    fun getAllTags(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getTagTitleList().also {
                tags.postValue(it)
            }

        }
    }
}