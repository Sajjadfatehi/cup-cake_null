package com.article.ui.viewmodel.providerfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.article.data.ArticleRepository
import com.article.ui.viewmodel.TitleViewModel

class TitleViewModelProviderFactory(val articleRepository: ArticleRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TitleViewModel(articleRepository) as T
    }
}