package com.user.data

import com.article.data.HomeLocalDataSource
import com.core.RetrofitUtil
import com.user.data.api.UserApi
import com.user.data.localdatasource.UserLocalDataSource
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.RegisterRequest
import com.user.data.reomtedatasource.UserRemote


    class UserRepository(val local: UserLocalDataSource, val remote: UserRemote) {

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

//    val localDataSource = LocalDataSource()
//    val userLocalDataSource=UserLocalDataSource()

//
//init {
//
//
//}
//
//    fun getPostInProf(): MutableList<ArticleView> {
//        return localDataSource.getPostInProf()
//    }