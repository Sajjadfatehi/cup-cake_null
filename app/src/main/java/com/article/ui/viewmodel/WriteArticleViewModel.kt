package com.article.ui.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.ArticleRepository
import com.article.data.modelfromservice.CreateArticleModel
import com.core.util.Resource
import com.google.android.material.chip.Chip
import com.user.data.modelfromservice.Article
import com.user.ui.ArticleView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class WriteArticleViewModel(val articleRepository: ArticleRepository) : ViewModel() {

    var articleInCreateArticleModel: MutableLiveData<Resource<Article>> = MutableLiveData()
    var activity: FragmentActivity = FragmentActivity()
    val tagsChip = mutableMapOf<Chip, String>()
    var argsFromProf: Bundle = Bundle()
    var isFromEdit = MutableLiveData<Boolean>()
    lateinit var article: ArticleView


    init {

    }

    fun createArticle(createArticleModel: CreateArticleModel) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = articleRepository.createArticle(createArticleModel = createArticleModel)
            Log.d("reqCj", "${response.isSuccessful}: ")
            articleInCreateArticleModel.postValue(handleCreateArticle(response))

        }

    fun handleCreateArticle(response: Response<Article>): Resource<Article> {
        if (response.isSuccessful) {
            Log.d("reqCj", "sjnfjkw ")
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun checkArgsIsNull(bundle: Bundle?) {

        if (bundle != null) {
            argsFromProf = bundle
        }
        if (!argsFromProf.isEmpty) {
            article = argsFromProf.getParcelable<ArticleView>("post")!!
            isFromEdit.value = true
        } else {
            isFromEdit.value = false
        }
        isFromEdit.value = bundle != null

    }


}



