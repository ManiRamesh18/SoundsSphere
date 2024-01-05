package com.example.soundssphere.data.network

import com.example.soundssphere.Constants
import com.example.soundssphere.data.network.response.model.AlbumsMetaDataResponse
import com.example.soundssphere.data.network.response.model.CategoriesListResponse
import com.example.soundssphere.data.network.response.model.CategoryPlayListResponse
import com.example.soundssphere.data.network.response.model.FeaturePlaylistResponse
import com.example.soundssphere.data.network.response.model.NewReleaseResponse
import com.example.soundssphere.data.network.token.BearerToken
import com.example.soundssphere.data.tokenManager.TokenRequest
import com.example.soundssphere.data.tokenManager.TokenResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApiService
{
    @POST(Constants.ACCOUNTS_URL)
    @FormUrlEncoded
    suspend fun generateNewAccessToken(@Body request : TokenRequest): Response<TokenResponse>

    @GET("new-releases")
    suspend fun getNewReleaseList(@Header("Authorization") bearerToken: BearerToken): NewReleaseResponse

    @GET("featured-playlists")
    suspend fun getFeaturePlayList(@Header("Authorization") bearerToken: BearerToken) : FeaturePlaylistResponse

    @GET("categories/{category_id}/playlists")
    suspend fun getCategoriesPlayList(@Header("Authorization") bearerToken: BearerToken,
                                      @Path("category_id") categoryId : String,
                                      @Query("limit") limit : Int = 5): CategoryPlayListResponse

    @GET("categories")
    suspend fun getCategories(@Header("Authorization") bearerToken: BearerToken,
                              @Query("limit") limit: Int = 20,
                              @Query("offset") offset : Int =0): CategoriesListResponse
}