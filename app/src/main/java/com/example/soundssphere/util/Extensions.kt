package com.example.soundssphere.util

import android.provider.MediaStore.Audio.Media
import androidx.media3.common.MediaItem
import com.example.soundssphere.domain.SearchResult

fun SearchResult.NewReleaseAlbumResult.toMediaItem(): MediaItem
{
    return  MediaItem.fromUri(this.trackUri)
}

