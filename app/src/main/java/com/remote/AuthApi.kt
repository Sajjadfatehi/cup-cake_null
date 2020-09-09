package com.remote


import com.part.myapplication.models.AuthRequest
import com.part.myapplication.models.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("users")
    fun register(@Body authRequest: AuthRequest): Call<AuthResponse>

    @POST("users/logiasdasn")
    suspend fun login(@Body authRequest: AuthRequest): AuthResponse
}