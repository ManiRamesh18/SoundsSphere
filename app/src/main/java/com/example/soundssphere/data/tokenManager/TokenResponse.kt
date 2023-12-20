package com.example.soundssphere.data.tokenManager

import com.google.gson.annotations.SerializedName

class TokenResponse {

    @SerializedName("access_token")
    val access_token : String ?= null

    @SerializedName("token_type")
    val token_type : String ?= null

    @SerializedName("expires_in")
    val expires_in : String ?= null
}