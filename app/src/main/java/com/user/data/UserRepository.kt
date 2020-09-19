package com.user.data

import com.core.LocalDataSource
import com.core.db.AppDataBase
import com.core.services.RetrofitService
import com.user.data.api.UserApi
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.reomtedatasource.UserRemote
import com.user.ui.ArticleView

class UserRepository(val db: AppDataBase) {
    val localDataSource = LocalDataSource()
    val userRemote = UserRemote()
    val retrofit = RetrofitService.retrofit.create(UserApi::class.java)

    init {


    }

    fun getPostInProf(): MutableList<ArticleView> {
        return localDataSource.getPostInProf()
    }

    suspend fun getAllArticleOfPerson(author: String, pageNumber: Int) =
        userRemote.getAllArticleOfPerson(author, pageNumber)


    suspend fun register(registerRequest: RegisterRequest) =
        userRemote.register(registerRequest)

    suspend fun favoriteArticle(slug: String) =
        userRemote.favoriteArticle(slug)

    suspend fun unFavoriteArticle(slug: String) =
        userRemote.unFavoriteArticle(slug)


    suspend fun favoritedArticleByUserName(favoritedUserName: String) =
        userRemote.favoritedArticleByUserName(favoritedUserName)

    suspend fun deleteArticle(slug: String) =
        userRemote.deleteArticle(slug)

    suspend fun profile(userName: String) =
        userRemote.profile(userName)

    suspend fun follow(userName: String, followRequest: FollowRequest) =
        userRemote.follow(userName, followRequest)

    suspend fun unFollow(userName: String) =
        userRemote.unFollow(userName)
}