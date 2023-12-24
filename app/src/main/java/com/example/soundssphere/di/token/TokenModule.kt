package com.example.soundssphere.di.token

import com.example.soundssphere.data.network.token.TokenManager
import com.example.soundssphere.data.network.token.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule
{

    @Binds
    @Singleton
    abstract fun provideTokenRepository(tokenManager: TokenManager): TokenRepository
}