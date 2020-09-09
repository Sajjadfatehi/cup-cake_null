package com.article.data.article

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.database.article.ArticleDataBaseDao

/**
 * Created by moha on 9/5/2020.
 */
@Suppress("UNCHECKED_CAST")
class ArticleViewModelFactory(
    private val dataSource: ArticleDataBaseDao,
    application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(dataSource, application = Application()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}