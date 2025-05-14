package com.clone.android.testvideo.ui.compose.home.components.player

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.clone.android.testvideo.data.entities.VideoEntity
import com.clone.android.testvideo.ui.compose.commons.OnLifecycleEvent
import com.clone.android.testvideo.ui.compose.commons.ProgressIndicator
import com.clone.android.testvideo.ui.compose.home.components.BottomData

@Composable
fun Media3PlayerView(
    videoUrl: String,
    context: Context,
    dataList: List<VideoEntity>,
    title: String,
    subTitle: String,
    progressState: MutableState<Boolean>,
    videoPlayerCache: VideoPlayerCache,
) {

    val player = remember { videoPlayerCache.getPlayer(videoUrl, context) }

    LaunchedEffect(videoUrl) {
        setupPlayer(videoUrl, player, dataList, videoPlayerCache, context, progressState)
    }

    DisposableEffect(Unit) {
        onDispose {
            videoPlayerCache.releasePlayer(videoUrl)
        }
    }

    OnLifecycleEvent { owner, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            videoPlayerCache.releaseAllPlayers()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Media3AndroidView(player)
        BottomData(title, subTitle)

        if (progressState.value) {
            ProgressIndicator()
        }

    }
}


private fun setupPlayer(
    videoUrl: String,
    player: ExoPlayer,
    dataList: List<VideoEntity>,
    videoPlayerCache: VideoPlayerCache,
    context: Context,
    progressState: MutableState<Boolean>
) {
    val mediaItem = MediaItem.fromUri(videoUrl.toUri())
    player.setMediaItem(mediaItem)
    player.seekTo(0)
    player.playWhenReady = true
    player.prepare()


    val currentIndex = dataList.indexOfFirst { it.link == videoUrl }
    if (currentIndex >= 0) {
        val videoUrlsToPreload = mutableListOf<String>()


        if (currentIndex > 0) {
            videoUrlsToPreload.add(dataList[currentIndex - 1].link)
        }


        if (currentIndex < dataList.size - 1) {
            videoUrlsToPreload.add(dataList[currentIndex + 1].link)
        }


        videoPlayerCache.preloadVideos(videoUrlsToPreload, context)
    }

    player.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {

            when (playbackState) {
                ExoPlayer.STATE_BUFFERING -> {
                    progressState.value = true
                }

                ExoPlayer.STATE_ENDED -> {
                    player.seekTo(0)
                    progressState.value = false
                }

                ExoPlayer.STATE_IDLE -> {
                    progressState.value = false
                }

                ExoPlayer.STATE_READY -> {
                    progressState.value = false
                }
            }
        }
    })
}


@OptIn(UnstableApi::class)
@Composable
fun Media3AndroidView(player: ExoPlayer?) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            PlayerView(context).apply {
                this.player = player
                useController = false
                this.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            }
        },
        update = { playerView ->
            playerView.player = player

            playerView.scaleX = 1.0f
            playerView.scaleY = 1.0f

            playerView.translationX = 0f
            playerView.translationY = 0f
        }
    )
}
