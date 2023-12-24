package com.example.soundssphere.data.network.response.model

data class AlbumsMetaDataResponse(
   val items : List<AlbumMetaDataResponse>,
   val limit : Int,
   var href : String,
   var next : String,
   var previous : String,
   var total : String
)

fun AlbumsMetaDataResponse.toAlbumsSearchResultList()= this.items.map { it.toAlbumSearchResult() }