package com.example.soundssphere.data.network

import com.example.soundssphere.Constants
import com.example.soundssphere.util.SoundsUtil
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        if(SoundsUtil.isUserLoggedIn())
        {
            return  chain.proceed(request.newBuilder().header("Authorization", "Bearer " + Constants.accessToken).build())
        }

        return chain.proceed(request)
    }
}