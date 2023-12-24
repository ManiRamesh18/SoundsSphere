package com.example.soundssphere.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.soundssphere.domain.SearchResult
import com.example.soundssphere.ui.HomeScreenViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navController: NavController)
{
    var showToast by remember{ mutableStateOf(false) }

    Scaffold(topBar = { TopBar() },
        bottomBar = { ShowCurrentPlayingSong()})
    {it->
        PlayList(viewModel){
            showToast =true
        }
    }
}

@Composable
fun PlayList(viewModel: HomeScreenViewModel, showToast : ()-> Unit)
{
    val scope = rememberCoroutineScope()

    Column (modifier = Modifier.fillMaxSize())
    {
        val context = LocalContext.current

        viewModel.newReleaseList.value.let { trackListResponse ->

                if (trackListResponse.errorMessage?.isNotEmpty() == true)
                {
                    Toast.makeText(context, trackListResponse.errorMessage?.toString(), Toast.LENGTH_LONG).show()

                }
                else
                {
                    if(trackListResponse.trackList.isNotEmpty())
                    {
                        LazyColumn{
                            items(trackListResponse.trackList){ track->
                                EachPlayList(track)
                            }
                        }
                    }
                }

        }

    }
}

@Composable
fun EachPlayList(track : SearchResult.NewReleaseAlbumResult)
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp))
    {
        Column (modifier = Modifier
            .fillMaxSize()
            .weight(0.8f))
        {
            Text(text = track.name,
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}
@Composable
fun ShowCurrentPlayingSong()
{

}
@Composable
fun TopBar()
{

}