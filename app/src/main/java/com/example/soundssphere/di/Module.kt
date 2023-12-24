package com.example.soundssphere.di

import com.example.soundssphere.Constants
import com.example.soundssphere.data.network.NetworkInterceptor
import com.example.soundssphere.data.network.RemoteApiService
import com.example.soundssphere.data.network.token.TokenRepository
import com.example.soundssphere.data.repo.SoundsSphereRepositoryImpl
import com.example.soundssphere.data.tokenManager.TokenRepositoryImpl
import com.example.soundssphere.ui.HomeScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object Module
{
    @Provides
    fun provideOkHttpClient():OkHttpClient
    {
        return OkHttpClient().newBuilder()
            .addInterceptor(NetworkInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit
    {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): RemoteApiService
    {
        return retrofit.create(RemoteApiService::class.java)
    }

    @Provides
    fun provideTokenRepoImpl(remoteApiService: RemoteApiService): TokenRepositoryImpl
    {
        return TokenRepositoryImpl(remoteApiService)
    }

    @Provides
    fun provideSoundsSphereRepoImpl(remoteApiService: RemoteApiService, tokenRepository: TokenRepository): SoundsSphereRepositoryImpl
    {
        return SoundsSphereRepositoryImpl(remoteApiService, tokenRepository)
    }

    @Provides
    fun provideHomeScreenViewModel(soundsSphereRepositoryImpl: SoundsSphereRepositoryImpl) : HomeScreenViewModel
    {
        return HomeScreenViewModel(soundsSphereRepositoryImpl)
    }
}