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
import com.user.data.modelfromservice.EditArticleRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class WriteArticleViewModel(val articleRepository: ArticleRepository) : ViewModel() {

    var articleInCreateArticleModel: MutableLiveData<Resource<Article>> = MutableLiveData()
    var activity: FragmentActivity = FragmentActivity()
    val tagsChip = mutableMapOf<Chip, String>()
    var argsFromProf: Bundle = Bundle()
    var isFromEdit = MutableLiveData<Boolean>()
    lateinit var article: Article

    var userName = ""
    var isUpdated = MutableLiveData<Boolean>()
    var editedArticle: MutableLiveData<Resource<Article>> = MutableLiveData()


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
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun updateArticle(slug: String, editArticleRequest: EditArticleRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            editedArticle.postValue(Resource.Loading())
            val response = articleRepository.updateArticle(slug, editArticleRequest)
            editedArticle.postValue(handleUpdateArticle(response))
        }

    private fun handleUpdateArticle(response: Response<Article>): Resource<Article> {
        if (response.isSuccessful) {

            isUpdated.postValue(true)
            response.body()?.let { result ->
                return Resource.Success(result)
            }

        }
        return Resource.Error(response.message())
    }


    fun checkArgsIsEmpty(bundle: Bundle?) {

        if (bundle != null) {
            if (!bundle.isEmpty) {

                bundle.getParcelable<Article>("post")?.let {
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


}



