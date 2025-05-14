package com.clone.android.testvideo.models

import androidx.media3.exoplayer.ExoPlayer

data class VideoCacheData(
    var exoPlayer: ExoPlayer,
    var id: Int,
)