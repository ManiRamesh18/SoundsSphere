package com.example.soundssphere.domain.myplayer

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.soundssphere.domain.SearchResult
import javax.inject.Inject

class MyPlayer @Inject constructor(private val exoPlayer: ExoPlayer): Player.Listener {


    fun initPlayList(trackList: List<MediaItem>)
    {
        exoPlayer.addListener(this)
        exoPlayer.setMediaItems(trackList)
        exoPlayer.prepare()
    }

    fun playTrack(index : Long)
    {
        exoPlayer.seekTo(index)
    }

    fun pauseTrack()
    {
        exoPlayer.playWhenReady = !exoPlayer.playWhenReady
    }

}