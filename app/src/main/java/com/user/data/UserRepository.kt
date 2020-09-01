package com.user.data

import com.core.LocalDataSource
import com.user.ui.PostInProfView

class UserRepository {
    val localDataSource = LocalDataSource()

    init {

    }

    fun getPostInProf(): MutableList<PostInProfView> {
        return localDataSource.getPostInProf()
    }

}