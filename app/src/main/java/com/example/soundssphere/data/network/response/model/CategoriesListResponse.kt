package com.example.soundssphere.data.network.response.model

import com.example.soundssphere.data.network.response.model.common.Image
import com.example.soundssphere.domain.SearchResult

data class CategoriesListResponse(val categories : Categories)
{
    data class Categories(val items : List<Category>)
    {
        data class Category(val id : String,val  name : String)
    }
}


data class CategoryPlayListResponse(val playlists : CategoryPlayList)
{
    data class CategoryPlayList(val items : List<PlayList>)
    {
        data class PlayList(val id : String,
            val name : String,
            val images: List<Image>)
    }
}

fun CategoryPlayListResponse.toSearchResultCategoryPlayList():List<SearchResult.Category>
{
    return this.playlists.items.map {
        SearchResult.Category(
            id = it.id,
            name = it.name,
            imageUrl = it.images[0].url
        )
    }
}
