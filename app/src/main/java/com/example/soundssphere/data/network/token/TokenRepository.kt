package com.example.soundssphere.data.network.token

interface TokenRepository
{
    suspend fun getValidBearerToken(): BearerToken
}