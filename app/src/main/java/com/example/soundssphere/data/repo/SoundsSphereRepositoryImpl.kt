package com.example.soundssphere.data.repo

import android.util.Log
import com.example.soundssphere.data.network.RemoteApiService
import com.example.soundssphere.data.network.response.model.toSearchAlbumResultList
import com.example.soundssphere.data.network.token.TokenRepository
import com.example.soundssphere.data.util.FetchedResource
import com.example.soundssphere.domain.SearchResult
import javax.inject.Inject

class SoundsSphereRepositoryImpl @Inject constructor(private val remoteApiService: RemoteApiService, private val tokenRepository: TokenRepository) : SoundsSphereRepository
{
    override suspend fun getNewReleaseList(): FetchedResource<List<SearchResult.NewReleaseAlbumResult>, String> {
        return tokenRepository.runBlocking {it->
            remoteApiService.getNewReleaseList(it).toSearchAlbumResultList()
        }
    }
}
