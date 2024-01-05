package com.example.soundssphere.ui.screens

import android.provider.Contacts.Intents.UI
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.soundssphere.R
import com.example.soundssphere.domain.SearchResult
import com.example.soundssphere.ui.HomeFeedCarousel
import com.example.soundssphere.ui.HomeFeedCarouselCardInfo
import com.example.soundssphere.ui.HomeScreenViewModel
import com.example.soundssphere.ui.UIState
import kotlinx.coroutines.launch
import java.lang.StringBuilder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navController: NavController)
{

    Scaffold(topBar = { },
        bottomBar = { ShowCurrentPlayingSong()},
    )
    {it->

        Column (modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(it))
        {
            val context = LocalContext.current

            val loadingAnimationComposition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(
                    R.raw.lottie_loading_anim
                )
            )
            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                visible = viewModel.uiState.value == HomeScreenViewModel.HomeFeedUiState.LOADING,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        modifier = Modifier.size(96.dp),
                        composition = loadingAnimationComposition,
                        iterations = LottieConstants.IterateForever
                    )
                }
            }

          if(viewModel.uiState.value == HomeScreenViewModel.HomeFeedUiState.IDLE)
          {
              HomeFeedCarouselList(homeFeedCarouselList = viewModel.carouselList.value)
          }
        }
    }
}


@Composable
fun HomeFeedCarouselList(homeFeedCarouselList : List<HomeFeedCarousel>)
{
    LazyColumn(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 15.dp))
    {
       items(homeFeedCarouselList)
       {
           Text(text = it.title)

           Spacer(modifier = Modifier.height(30.dp))

           HomeCarouselCardInfoList(it.associatedHomeCarouselInfo)
       }
    }
}

@Composable
fun HomeCarouselCardInfoList(homeFeedCarouselCardInfo: List<HomeFeedCarouselCardInfo>)
{
    LazyRow(modifier = Modifier
        .height(500.dp)
        .fillMaxWidth())
    {
        items(homeFeedCarouselCardInfo)
        {it->
            EachPlayList(track = it) {
            }
        }
    }
}

@Composable
fun EachPlayList(track : HomeFeedCarouselCardInfo, onSongClick:()->Unit)
{
    Box (modifier = Modifier
        .background(Color.White)
        .clip(RoundedCornerShape(CornerSize(3.dp)))
        .padding(3.dp))
    {
        Card (modifier = Modifier
            .wrapContentHeight()
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .fillMaxWidth())
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(track.imageUrlString)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.FillWidth)
        }
    }

}

@Composable
fun ShowCurrentPlayingSong()
{

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBar()
{
    Row(modifier = Modifier
        .padding(5.dp)
        .wrapContentHeight())
    {
       Column(modifier = Modifier
           .wrapContentHeight()
           .fillMaxWidth()) {
           Text(text = "Search Bar")

           val list = arrayListOf<String>("All", "Albums", "Playlist", "Recently Played", "Artist", "Explore")
           LazyRow(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 20.dp))
           {
              items(list)
              {item->
                  Text(
                      text = "Item $item",
                      modifier = Modifier
                          .padding(end = 10.dp)
                          .clip(RoundedCornerShape(10.dp))
                          .background(color = Color.LightGray)
                          .padding(16.dp)
                  )
              }
           }
       }
    }
}

