package com.article.data.tag

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.article.data.article.ArticleViewModel
import com.article.data.comment.CommentViewModel
import com.database.article.ArticleDataBaseDao
import com.database.comment.CommentDataBaseDao
import com.database.tag.TagDataBaseDao

/**
 * Created by moha on 9/5/2020.
 */
@Suppress("UNCHECKED_CAST")
class TagViewModelFactory(
    private val dataSource: TagDataBaseDao,
    application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagViewModel::class.java)) {
            return TagViewModel(dataSource, application = Application()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}