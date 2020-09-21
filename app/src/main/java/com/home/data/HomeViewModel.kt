package com.home.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.*
import com.core.ResultCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by moha on 9/15/2020.
 */
class HomeViewModel(val repo : ArticleRepository):ViewModel() {

    val tags = MutableLiveData<List<TagModel>>()
    val tag = String
    val articles = MutableLiveData<List<ArticleUser>>()

    fun getAllTags(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getTagTitleList0().also {
                tags.postValue(it)
            }

        }
    }

    fun getArticleWithTag(text :String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getArticleWithTag(text).also {
                articles.postValue(it)
            }
        }
    }
}