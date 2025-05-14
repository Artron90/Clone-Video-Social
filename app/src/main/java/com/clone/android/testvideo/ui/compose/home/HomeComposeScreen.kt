package com.clone.android.testvideo.ui.compose.home

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.clone.android.testvideo.data.entities.VideoEntity
import com.clone.android.testvideo.ui.compose.commons.OnLifecycleEvent
import com.clone.android.testvideo.ui.compose.home.components.player.Media3PlayerView
import com.clone.android.testvideo.ui.compose.home.components.player.VideoPlayerCache


@Composable
fun HomeComposeScreen(
    homeComposeViewModel: HomeComposeViewModel
) {

    homeComposeViewModel.getVideo()
    val videoPlayerCache = remember { VideoPlayerCache() }

    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            TestComposeContent(
                homeComposeViewModel = homeComposeViewModel,
                videoPlayerCache = videoPlayerCache
            )

        }

    }

    OnLifecycleEvent { owner, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            homeComposeViewModel.clearVideoData()
            videoPlayerCache.releaseAllPlayers()
            homeComposeViewModel.compositeDisposable.dispose()
        }
    }

}


@Composable
fun TestComposeContent(
    homeComposeViewModel: HomeComposeViewModel,
    videoPlayerCache: VideoPlayerCache
) {

    VerticalPagerVideo(
        homeComposeViewModel = homeComposeViewModel,
        context = LocalContext.current,
        videoPlayerCache = videoPlayerCache
    )

}


@Composable
fun VerticalPagerVideo(
    homeComposeViewModel: HomeComposeViewModel,
    context: Context,
    videoPlayerCache: VideoPlayerCache
) {
    val itemsFromDb by homeComposeViewModel.dataResponse.collectAsState(mutableListOf())
    var items = remember { mutableStateListOf<VideoEntity>() }
    val pagerState = rememberPagerState { items.size }
    var progressState = homeComposeViewModel.progress

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { currentPage ->
            if (currentPage == items.size - 1 && items.isNotEmpty()) {
                progressState.value = true
                homeComposeViewModel.setPage()
                homeComposeViewModel.getVideo()
            }
        }
    }

    LaunchedEffect(itemsFromDb) {
        snapshotFlow { itemsFromDb }.collect { currentData ->
            if (currentData.isNotEmpty() && items.isEmpty()) {
                items.addAll(currentData)
                progressState.value = false
            } else {
                val newItems = currentData.filter { data -> !items.contains(data) }
                if (newItems.isNotEmpty()) {
                    items.addAll(newItems)
                }
                progressState.value = false
            }
        }
    }



    PageContent(
        pagerState = pagerState,
        context = context,
        dataList = items,
        progressState = progressState,
        videoPlayerCache = videoPlayerCache
    )
}

@Composable
fun PageContent(
    context: Context = LocalContext.current,
    dataList: List<VideoEntity> = listOf(),
    pagerState: PagerState = rememberPagerState { 0 },
    progressState: MutableState<Boolean> = mutableStateOf(false),
    videoPlayerCache: VideoPlayerCache
) {

    VerticalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {

        Media3PlayerView(
            videoUrl = dataList[it].link,
            context = context,
            dataList = dataList,
            title = dataList[it].name,
            subTitle = dataList[it].quality,
            progressState = progressState,
            videoPlayerCache = videoPlayerCache
        )

    }

}



