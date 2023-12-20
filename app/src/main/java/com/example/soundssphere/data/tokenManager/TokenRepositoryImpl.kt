package com.example.soundssphere.data.tokenManager

import android.util.Log
import com.example.soundssphere.data.network.RemoteApiService
import com.example.soundssphere.util.SoundsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.lang.Exception
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val remoteApiService: RemoteApiService): TokenRepo
{
    override suspend fun generateAccessToken(): String
    {
        return withContext(Dispatchers.IO)
        {
            try {
                val response = remoteApiService.generateNewAccessToken(SoundsUtil.constructHeaders())

                if(response.isSuccessful)
                {
                    Log.d("RESPONSE", (response as TokenResponse).access_token!!)
                }
                else{
                    Log.d("RESPONSE", (response as TokenResponse).access_token.orEmpty())

                }

                ""
            }catch (e : Exception)
            {
                Log.d("RESPONSE", e.message.toString())
                ""
            }
        }
    }
}