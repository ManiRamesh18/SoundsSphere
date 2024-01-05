package com.example.soundssphere.util

import com.example.soundssphere.Constants
import com.example.soundssphere.SoundsSphereApplication
import com.example.soundssphere.data.tokenManager.TokenRequest

object SoundsUtil
{
    fun constructHeaders()= run {
        TokenRequest(grant_type = Constants.GRANT_TYPE, client_id = Constants.CLIENT_ID, client_secret = Constants.CLIENT_SECRET)
    }

    fun isValidToken(): Boolean = kotlin.run {
        return true}

    fun isUserLoggedIn(): Boolean
    {
        return Constants.accessToken.isNotEmpty()
    }
}