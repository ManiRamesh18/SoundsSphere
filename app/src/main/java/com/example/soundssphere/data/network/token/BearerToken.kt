package com.example.soundssphere.data.network.token

data class BearerToken(
    private val token : String){
    val value  get() = "Bearer $token"
}
