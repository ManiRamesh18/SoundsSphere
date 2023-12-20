package com.example.soundssphere.data.tokenManager

import com.example.soundssphere.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRepo
{
    suspend fun generateAccessToken(): String
}