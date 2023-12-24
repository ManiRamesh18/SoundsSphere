package com.example.soundssphere.data.network

import com.example.soundssphere.Constants
import com.example.soundssphere.data.network.response.model.AlbumsMetaDataResponse
import com.example.soundssphere.data.network.response.model.NewReleaseResponse
import com.example.soundssphere.data.network.token.BearerToken
import com.example.soundssphere.data.tokenManager.TokenRequest
import com.example.soundssphere.data.tokenManager.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RemoteApiService
{
    @POST(Constants.ACCOUNTS_URL)
    @FormUrlEncoded
    suspend fun generateNewAccessToken(@Body request : TokenRequest): Response<TokenResponse>

    @GET("browse/new-releases")
    suspend fun getNewReleaseList(@Header("Authorization") bearerToken: BearerToken): NewReleaseResponse
}