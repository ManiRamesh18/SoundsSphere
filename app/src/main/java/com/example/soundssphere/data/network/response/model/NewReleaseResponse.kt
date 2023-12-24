package com.example.soundssphere.data.network.response.model

data class NewReleaseResponse(val albums : Albums){
    data class Albums(val items : List<AlbumMetaDataResponse>)
}

fun NewReleaseResponse.toSearchAlbumResultList() = this.albums.items.map { it.toAlbumSearchResult() }