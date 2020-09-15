package com.article.ui.viewmodel.providerfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.article.data.ArticleRepository
import com.article.ui.viewmodel.WriteArticleViewModel

class WriteViewModelProviderFactory(val articleRepository: ArticleRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WriteArticleViewModel(articleRepository) as T
    }
}