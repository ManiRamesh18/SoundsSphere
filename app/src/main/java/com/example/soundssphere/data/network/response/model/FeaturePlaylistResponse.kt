package com.example.soundssphere.data.network.response.model

import com.example.soundssphere.data.network.response.model.common.Image
import com.example.soundssphere.domain.SearchResult

data class FeaturePlaylistResponse(val playlists : PlayList)
{
    data class PlayList(
        val items : List<Track>
    ){
        data class Track(
            val id : String,
            val name : String,
            val images : List<Image>,
            val tracks : TrackURL
        ) {
            data class TrackURL(
                val href : String,
                val total : String
            )
        }
    }
}



fun FeaturePlaylistResponse.PlayList.Track.toPlayList(): SearchResult.FeaturePlayList
{
    return SearchResult.FeaturePlayList(
        id = this.id,
        name=this.name,
        imageUrl = this.images[0].url,
        tracksUrl = this.tracks.href
    )
}

fun FeaturePlaylistResponse.toPlayList(): List<SearchResult.FeaturePlayList>
{
    return this.playlists.items.map { it.toPlayList() }
}