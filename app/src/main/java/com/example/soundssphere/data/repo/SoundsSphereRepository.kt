package com.example.soundssphere.data.repo

import com.example.soundssphere.data.util.FetchedResource
import com.example.soundssphere.domain.SearchResult

interface SoundsSphereRepository
{
    suspend fun getNewReleaseList() : FetchedResource<List<SearchResult.NewReleaseAlbumResult>, String>

    suspend fun getFeaturedPlaylist() : FetchedResource<List<SearchResult.FeaturePlayList>, String>

    suspend fun getCategoriesPlayList() : FetchedResource<List<SearchResult.Categories>, String>
}