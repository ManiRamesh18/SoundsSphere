package com.example.soundssphere.ui

import android.provider.Contacts.Intents.UI
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundssphere.data.repo.SoundsSphereRepositoryImpl
import com.example.soundssphere.data.util.FetchedResource
import com.example.soundssphere.domain.SearchResult
import com.example.soundssphere.domain.myplayer.MyPlayer
import com.example.soundssphere.domain.toHomeCarouselCard
import com.example.soundssphere.util.toMediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val soundsSphereRepositoryImpl: SoundsSphereRepositoryImpl, private val myPlayer: MyPlayer): ViewModel()
{
    private var _uiState : MutableState<HomeFeedUiState> = mutableStateOf(HomeFeedUiState.LOADING)
     var uiState : State<HomeFeedUiState> = _uiState

    private var _carouselList : MutableState<List<HomeFeedCarousel>> = mutableStateOf(emptyList())
    var carouselList : State<List<HomeFeedCarousel>> =_carouselList

    init {
        getNewReleaseList()
    }

    private fun initList(playerList :List<SearchResult.NewReleaseAlbumResult>)
    {
        myPlayer.initPlayList(playerList.map { it.toMediaItem() })
    }

     fun play(index : Long)
    {
        myPlayer.playTrack(index)
    }

    fun pause()
    {
        myPlayer.pauseTrack()
    }

    private fun getNewReleaseList()
    {
        viewModelScope.launch {

            _uiState.value = HomeFeedUiState.LOADING
            var carouselList = mutableListOf<HomeFeedCarousel>()


            val featurePlayList = async { soundsSphereRepositoryImpl.getFeaturedPlaylist() }
            featurePlayList.awaitFetchedUpdateUiState {

                carouselList.add(
                     HomeFeedCarousel(
                         id = "Featured PlayList",
                         title = "Featured PlayList",
                        associatedHomeCarouselInfo = it.map<SearchResult, HomeFeedCarouselCardInfo>(::toHomeFeedCarouselCardInfo)
                        )
                )

                _carouselList.value = carouselList
            }

            val categoriesPlayList = async { soundsSphereRepositoryImpl.getCategoriesPlayList() }
            categoriesPlayList.awaitFetchedUpdateUiState { it->
                it.map { playListCategories -> playListCategories.toHomeCarouselCard() }
                    .forEach(carouselList::add)


                _carouselList.value = carouselList
            }

            _uiState.value = HomeFeedUiState.IDLE
        }
    }

    private suspend fun <FetchedResourceType> Deferred<FetchedResource<FetchedResourceType, String>>.awaitFetchedUpdateUiState(
        onSuccess: (FetchedResourceType)-> Unit)
    {
       this.awaitFetchedResource(onError = { it->
            _uiState.value = HomeFeedUiState.ERROR
       },
           onSuccess = { onSuccess(it)})
    }

    private suspend fun <FetchedResourceType> Deferred<FetchedResource<FetchedResourceType, String>>.awaitFetchedResource(
        onError : (String)-> Unit, onSuccess : (FetchedResourceType)-> Unit)
    {
            val response = this.await()

           if(response !is FetchedResource.Success)
           {
               onError(response.toString())
               return
           }

        onSuccess(response.data)
    }

    enum class HomeFeedUiState { IDLE, LOADING, ERROR }
}


fun toHomeFeedCarouselCardInfo(searchResult: SearchResult): HomeFeedCarouselCardInfo
    {
        return when(searchResult)
        {
            is SearchResult.FeaturePlayList ->
            {
                HomeFeedCarouselCardInfo(
                    id = searchResult.id,
                    imageUrlString = searchResult.imageUrl,
                    caption =  searchResult.name,
                    associatedSearchResult = searchResult
                )
            }

            is SearchResult.NewReleaseAlbumResult ->{

                HomeFeedCarouselCardInfo(
                    id = searchResult.id,
                    imageUrlString = searchResult.imageUrl,
                    caption =  searchResult.name,
                    associatedSearchResult = searchResult
                )
            }
            else ->
            {

                HomeFeedCarouselCardInfo(
                    id = "searchResult.id",
                    imageUrlString =" searchResult.images",
                    caption =  "",
                    associatedSearchResult = searchResult
                )
            }
        }
    }



data class HomeFeedCarouselCardInfo(
    val id: String,
    val imageUrlString: String,
    val caption: String,
    val associatedSearchResult: SearchResult
)

data class HomeFeedCarousel(
    val id : String,
    val title : String,
    val associatedHomeCarouselInfo : List<HomeFeedCarouselCardInfo>
)



sealed class UIState{
    class Success(val data: List<HomeFeedCarousel> ?= null): UIState()

    object Loading : UIState()

    class Error(val errorMessage : String) : UIState()
}
