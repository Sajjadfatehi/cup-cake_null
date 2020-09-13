package com.user.data

import retrofit2.Response
import retrofit2.http.*


/**
 * Created by moha on 9/12/2020.
 */

interface UserApi {

    @POST("users/login")
    suspend fun login(@Body loginReq: LoginReq): Response<LoginRes>

}