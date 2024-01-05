package com.example.soundssphere.data.repo

import com.example.soundssphere.data.network.RemoteApiService
import com.example.soundssphere.data.network.response.model.toPlayList
import com.example.soundssphere.data.network.response.model.toSearchAlbumResultList
import com.example.soundssphere.data.network.response.model.toSearchResultCategoryPlayList
import com.example.soundssphere.data.network.token.TokenRepository
import com.example.soundssphere.data.util.FetchedResource
import com.example.soundssphere.domain.SearchResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class SoundsSphereRepositoryImpl @Inject constructor(private val remoteApiService: RemoteApiService, private val tokenRepository: TokenRepository) : SoundsSphereRepository
{
    override suspend fun getNewReleaseList(): FetchedResource<List<SearchResult.NewReleaseAlbumResult>, String> {
        return tokenRepository.runBlocking {it->
            remoteApiService.getNewReleaseList(it).toSearchAlbumResultList()
        }
    }

    override suspend fun getFeaturedPlaylist(): FetchedResource<List<SearchResult.FeaturePlayList>, String> {
        return tokenRepository.runBlocking {
            remoteApiService.getFeaturePlayList(it).toPlayList()
        }
    }

    override suspend fun getCategoriesPlayList(): FetchedResource<List<SearchResult.Categories>, String> {
        return tokenRepository.runBlocking {bearerToken->
           val response = remoteApiService.getCategories(bearerToken).categories.items

          coroutineScope {
              val playList = response.map { it->
                  async {
                      remoteApiService.getCategoriesPlayList(bearerToken, categoryId = it.id).toSearchResultCategoryPlayList()
                  }
              }

              playList.awaitAll().mapIndexed { index, playlists ->
                  SearchResult.Categories(
                      id = response[index].id,
                      name = response[index].name,
                      playlists = playlists
                  )
              }
          }
        }
    }

}
