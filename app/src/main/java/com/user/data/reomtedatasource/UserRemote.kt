package com.user.data.reomtedatasource

import com.core.RetrofitUtil
import com.user.data.api.UserApi
import com.user.data.modelfromservice.FollowRequest
import com.user.data.modelfromservice.RegisterRequest

class UserRemote {
    val retrofit = RetrofitUtil.getInstance().create(UserApi::class.java)


    suspend fun getAllArticleOfPerson(author: String, pageNumber: Int) =
        retrofit.getAllArticleOfPerson(author, pageNumber)


    suspend fun register(registerRequest: RegisterRequest) =
        retrofit.register(registerRequest)

    suspend fun favoriteArticle(slug: String) =
        retrofit.favoriteArticle(slug)

    suspend fun unFavoriteArticle(slug: String) =
        retrofit.unFavoriteArticle(slug)


    suspend fun favoritedArticleByUserName(favoritedUserName: String) =
        retrofit.favoriteArticleByUserName( favoritedUserName)

    suspend fun deleteArticle(slug: String) =
        retrofit.deleteArticle(slug)

    suspend fun profile(userName: String) =
        retrofit.profile(userName)

    suspend fun follow(userName:String,followRequest: FollowRequest)=
        retrofit.follow(userName,followRequest )

    suspend fun unFollow(userName:String)=
        retrofit.unFollow(userName)
}