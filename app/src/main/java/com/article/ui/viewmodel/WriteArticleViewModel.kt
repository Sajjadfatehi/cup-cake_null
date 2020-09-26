package com.article.ui.viewmodel

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.ArticleDataEntity
import com.article.data.ArticleRepository
import com.article.data.modelfromservice.ArticleResponse
import com.article.data.modelfromservice.CreateArticleModel
import com.core.util.Resource
import com.google.android.material.chip.Chip
import com.user.data.modelfromservice.EditArticleRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WriteArticleViewModel(val articleRepository: ArticleRepository) : ViewModel() {

    var articleInCreateArticleModel: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()
    var activity: FragmentActivity = FragmentActivity()
    val tagsChip = mutableMapOf<Chip, String>()
    var argsFromProf: Bundle = Bundle()
    var isFromEdit = MutableLiveData<Boolean>()
    lateinit var article: ArticleDataEntity

    var userName = ""
    var isUpdated = MutableLiveData<Boolean>()
    var editedArticle: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()


    init {

    }

    fun createArticle(createArticleModel: CreateArticleModel) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = articleRepository.createArticle(createArticleModel = createArticleModel)

            articleInCreateArticleModel.postValue(response)

        }


    fun updateArticle(slug: String, editArticleRequest: EditArticleRequest) = viewModelScope.launch(Dispatchers.IO) {
            editedArticle.postValue(Resource.Loading())
            val response = articleRepository.updateArticle(slug, editArticleRequest)
            editedArticle.postValue(response)
        }

    fun checkArgsIsEmpty(bundle: Bundle?) {

        if (bundle != null) {
            if (!bundle.isEmpty) {

                bundle.getParcelable<ArticleDataEntity>("post")?.let {
                    article = it
                    isFromEdit.value = true

                }
                bundle.getString("userName")?.let {
                    userName = it
                }

            } else {
                isFromEdit.value = false
            }
        }


    }

    fun getTitleFromShare() = articleRepository.getTitleFromShare()

    fun getBodyFromShare() = articleRepository.getBodyFromShare()

    fun saveTitleInSharePref(title: String) {
        articleRepository.saveTitleInSharePref(title)
    }

    fun saveBodyInSharePref(body: String) {
        articleRepository.saveBodyInSharePref(body)
    }


}



