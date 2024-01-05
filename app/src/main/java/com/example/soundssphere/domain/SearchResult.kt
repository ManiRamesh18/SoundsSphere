package com.example.soundssphere.domain

import com.example.soundssphere.ui.HomeFeedCarousel
import com.example.soundssphere.ui.HomeFeedCarouselCardInfo

sealed class SearchResult{

    data class NewReleaseAlbumResult(
        val id : String,
        val name : String,
        val artists: List<Artists>,
        val trackUri : String,
        val imageUrl : String
    ): SearchResult()

    data class Artists(
        val id : String,
        val name : String
    ): SearchResult()


    data class FeaturePlayList(
        val id : String,
        val name : String,
        val imageUrl : String,
        val tracksUrl : String
    ): SearchResult()

    data class Categories(
        val id: String,
        val name : String,
        val playlists : List<Category>
    ): SearchResult()

    data class Category(
        val id : String,
        val name : String,
        val imageUrl: String,
    ): SearchResult()
}


fun SearchResult.Categories.toHomeCarouselCard(): HomeFeedCarousel = playlists.map {
    HomeFeedCarouselCardInfo(
        id = it.id,
        caption = it.name,
        imageUrlString = it.imageUrl,
        associatedSearchResult = it
    )
}.let { it->
    HomeFeedCarousel(
        id = id,
        title = name,
        associatedHomeCarouselInfo = it
    )
}
