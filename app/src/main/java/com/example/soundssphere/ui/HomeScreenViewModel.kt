package com.example.soundssphere.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundssphere.data.repo.SoundsSphereRepositoryImpl
import com.example.soundssphere.data.util.FetchedResource
import com.example.soundssphere.domain.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val soundsSphereRepositoryImpl: SoundsSphereRepositoryImpl): ViewModel()
{
    private val _newReleaseList : MutableState<TrackListResponse> = mutableStateOf(TrackListResponse(isLoading = true))
    val newReleaseList : State<TrackListResponse> = _newReleaseList

    init {
        getNewReleaseList()
    }

    private fun getNewReleaseList()
    {
        viewModelScope.launch {

            val newAlbums = async { soundsSphereRepositoryImpl.getNewReleaseList() }
            newAlbums.awaitFetchedUpdateUiState {trackList ->
                _newReleaseList.value = TrackListResponse(isLoading = false, trackList = trackList)
            }
        }
    }

    private suspend fun <FetchedResourceType> Deferred<FetchedResource<FetchedResourceType, String>>.awaitFetchedUpdateUiState(
        onSuccess: (FetchedResourceType)-> Unit)
    {
       this.awaitFetchedResource(onError = { it->
            _newReleaseList.value = TrackListResponse(isLoading = false, errorMessage = it)
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
}




data class TrackListResponse(
    val isLoading : Boolean = false,
    val trackList : List<SearchResult.NewReleaseAlbumResult> = emptyList(),
    val errorMessage : String ?= null

)