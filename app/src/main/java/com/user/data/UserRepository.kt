package com.user.data

import com.core.LocalDataSource
import com.core.RemoteDataSource
import com.core.db.AppDataBase
import com.core.services.RetrofitService
import com.user.data.api.UserApi
import com.user.data.localdatasource.UserLocalDataSource
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.reomtedatasource.UserRemote
import com.user.ui.ArticleView

class UserRepository(val local:UserLocalDataSource,val remote:UserRemote) {
//    val localDataSource = LocalDataSource()
//    val userLocalDataSource=UserLocalDataSource()

    val retrofit = RetrofitService.retrofit.create(UserApi::class.java)

    init {


    }
//
//    fun getPostInProf(): MutableList<ArticleView> {
//        return localDataSource.getPostInProf()
//    }

    suspend fun getAllArticleOfPerson(author: String, pageNumber: Int) =
        remote.getAllArticleOfPerson(author, pageNumber)


    suspend fun register(registerRequest: RegisterRequest) =
        remote.register(registerRequest)

    suspend fun favoriteArticle(slug: String) =
        remote.favoriteArticle(slug)

    suspend fun unFavoriteArticle(slug: String) =
        remote.unFavoriteArticle(slug)


    suspend fun favoritedArticleByUserName(favoritedUserName: String) =
        remote.favoritedArticleByUserName(favoritedUserName)

    suspend fun deleteArticle(slug: String) =
        remote.deleteArticle(slug)

    suspend fun profile(userName: String) =
        remote.profile(userName)

    suspend fun follow(userName: String, followRequest: FollowRequest) =
        remote.follow(userName, followRequest)

    suspend fun unFollow(userName: String) =
        remote.unFollow(userName)
}