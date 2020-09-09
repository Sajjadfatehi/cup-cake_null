package com.article.data.comment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.article.data.article.ArticleViewModel
import com.database.article.ArticleDataBaseDao
import com.database.comment.CommentDataBaseDao

/**
 * Created by moha on 9/5/2020.
 */
@Suppress("UNCHECKED_CAST")
class CommentViewModelFactory(
    private val dataSource: CommentDataBaseDao,
    application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            return CommentViewModel(dataSource, application = Application()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}