package com.example.soundssphere.data.repo

import com.example.soundssphere.data.network.token.BearerToken
import com.example.soundssphere.data.network.token.TokenRepository
import com.example.soundssphere.data.util.FetchedResource


suspend fun <T> TokenRepository.runBlocking(block : suspend (BearerToken) -> T): FetchedResource<T, String> = try {
        FetchedResource.Success(data = block(getValidBearerToken()))
    }catch (e : Exception)
    {
        FetchedResource.Failure( e.message.toString())
    }
