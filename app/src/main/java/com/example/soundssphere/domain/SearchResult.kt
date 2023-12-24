package com.example.soundssphere.domain

sealed class SearchResult{

    data class NewReleaseAlbumResult(
        val id : String,
        val name : String
    ): SearchResult()
}
