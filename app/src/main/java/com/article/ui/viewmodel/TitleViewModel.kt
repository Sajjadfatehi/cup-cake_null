package com.article.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.article.data.ArticleRepository
import com.core.util.Resource
import com.home.ui.PersonArticleModelView
import com.user.data.modelfromservice.AllArticleOfPerson
import kotlinx.coroutines.launch
import retrofit2.Response

class TitleViewModel(val articleRepository: ArticleRepository) : ViewModel() {

    var list = MutableLiveData<MutableList<PersonArticleModelView>>()


    val articlesByTag: MutableLiveData<Resource<AllArticleOfPerson>> =
        MutableLiveData()
    var articlesByTagPage = 1
    var articlesByTagResponse: AllArticleOfPerson? = null

    fun getArticlesByTag(tag: String) = viewModelScope.launch {
        articlesByTag.postValue(Resource.Loading())
        val response = articleRepository.getArticleByTag(tag, articlesByTagPage)
        articlesByTag.postValue(handleArticlesByTagResponse(response))

    }

    fun handleArticlesByTagResponse(response: Response<AllArticleOfPerson>): Resource<AllArticleOfPerson> {
        if (response.isSuccessful) {
//            Log.d("lashii", "${response.body()?.articles?.get(0)?.slug} ")
            response.body()?.let { resultResponse ->
                articlesByTagPage++
                if (articlesByTagResponse == null) {
                    articlesByTagResponse = resultResponse
                } else {
                    val oldArticles = articlesByTagResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success(articlesByTagResponse ?: resultResponse)

            }
        }

        return Resource.Error(response.message())
    }




    init {
//        list.value = articleRepository.getTagTitleList()

        getArticlesByTag("dragons")


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