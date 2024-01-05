package com.example.soundssphere.data.network.response.model

import com.example.soundssphere.data.network.response.model.common.Image
import com.example.soundssphere.domain.SearchResult
import com.google.gson.annotations.SerializedName

data class AlbumMetaDataResponse(
    @SerializedName("album_type")
    val albumType: String,

    val href: String,

    val id: String,

    val images: List<Image>,

    val name: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("artists")
    var artists : List<ArtistInfoResponse>,

    @SerializedName("release_date_precision")
    val releaseDatePrecision: String,

    @SerializedName("total_tracks")
    val totalTracks: Int,

    val type: String,

    val uri: String,
){

    data class ArtistInfoResponse(val id : String, val name : String)
}

fun AlbumMetaDataResponse.ArtistInfoResponse.toArtistsInfoSearchResult(): SearchResult.Artists
{
    return SearchResult.Artists(
        id = this.id,
        name = this.name,
    )
}

fun AlbumMetaDataResponse.toAlbumSearchResult(): SearchResult.NewReleaseAlbumResult
{
    return SearchResult.NewReleaseAlbumResult(
        id = this.id,
        name = this.name,
        artists = this.artists.map { it.toArtistsInfoSearchResult() },
        trackUri = this.href,
        imageUrl = this.images[0].url
    )
}