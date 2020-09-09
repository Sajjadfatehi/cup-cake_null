package com.article.data.user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.article.data.article.ArticleViewModel
import com.database.article.ArticleDataBaseDao
import com.database.user.UserDataBaseDao

/**
 * Created by moha on 9/5/2020.
 */
@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val dataSource: UserDataBaseDao,
    application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(dataSource, application = Application()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}