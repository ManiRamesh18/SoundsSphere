package com.example.soundssphere.data.network.response.model

import com.example.soundssphere.domain.SearchResult
import com.google.gson.annotations.SerializedName

data class AlbumMetaDataResponse(
    @SerializedName("album_type")
    val albumType: String,

    val href: String,

    val id: String,

    val images: List<MovieImage>,

    val name: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("release_date_precision")
    val releaseDatePrecision: String,

    @SerializedName("total_tracks")
    val totalTracks: Int,

    val type: String,

    val uri: String,
){
    data class MovieImage(var height : Int, var url : String, var width : String)
}

fun AlbumMetaDataResponse.toAlbumSearchResult(): SearchResult.NewReleaseAlbumResult
{
    return SearchResult.NewReleaseAlbumResult(
        id = this.id,
        name = this.name
    )
}