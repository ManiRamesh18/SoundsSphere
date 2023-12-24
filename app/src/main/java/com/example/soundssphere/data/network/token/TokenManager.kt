package com.example.soundssphere.data.network.token

import com.example.soundssphere.Constants
import javax.inject.Inject

class TokenManager @Inject constructor(): TokenRepository
{
    private var bearerToken : BearerToken ?= null
    override suspend fun getValidBearerToken(): BearerToken {

        bearerToken = BearerToken(Constants.accessToken)
        return bearerToken as BearerToken
    }
}