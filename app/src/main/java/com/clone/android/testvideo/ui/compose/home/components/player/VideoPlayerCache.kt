package com.clone.android.testvideo.ui.compose.home.components.player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import com.clone.android.testvideo.configuration.Constant

class VideoPlayerCache {
    private val playersCache = mutableMapOf<String, ExoPlayer>()

    fun getPlayer(videoUrl: String, context: Context): ExoPlayer {
        return playersCache[videoUrl] ?: createAndCachePlayer(videoUrl, context)
    }


    @OptIn(UnstableApi::class)
    private fun createAndCachePlayer(videoUrl: String, context: Context): ExoPlayer {
        val player = ExoPlayer.Builder(context).setLoadControl(
            DefaultLoadControl.Builder()
                .setPrioritizeTimeOverSizeThresholds(false)
                .setBufferDurationsMs(
                    Constant.DEFAULT_MIN_BUFFER_MS,
                    Constant.DEFAULT_MAX_BUFFER_MS,
                    Constant.DEFAULT_MIN_BUFFER_FOR_PLAY_BACK,
                    Constant.DEFAULT_MIN_BUFFER_AFTER_RE_BUFFER
                ).build()
        ).build()
        playersCache[videoUrl] = player
        return player
    }

    fun preloadVideos(videoUrls: List<String>, context: Context) {
        for (url in videoUrls) {
            getPlayer(url, context)
        }
    }

    fun releasePlayer(videoUrl: String) {
        playersCache[videoUrl]?.release()
        playersCache.remove(videoUrl)
    }

    fun releaseAllPlayers() {
        for (player in playersCache.values) {
            player.release()
        }
        playersCache.clear()
    }
}