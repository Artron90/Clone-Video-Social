package com.clone.android.testvideo.ui.fragment.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.extractor.ExtractorsFactory
import androidx.recyclerview.widget.RecyclerView
import com.clone.android.testvideo.configuration.Constant.DEFAULT_MAX_BUFFER_MS
import com.clone.android.testvideo.configuration.Constant.DEFAULT_MIN_BUFFER_AFTER_RE_BUFFER
import com.clone.android.testvideo.configuration.Constant.DEFAULT_MIN_BUFFER_FOR_PLAY_BACK
import com.clone.android.testvideo.configuration.Constant.DEFAULT_MIN_BUFFER_MS
import com.clone.android.testvideo.configuration.Constant.NUMBER_CONTENT_CACHE
import com.clone.android.testvideo.data.entities.VideoEntity
import com.clone.android.testvideo.databinding.ItemContainerVideoBinding
import com.clone.android.testvideo.models.VideoCacheData


class CustomVideoAdapter(
    private var dataList: MutableList<VideoEntity>,
    private val context: Context
) :
    RecyclerView.Adapter<CustomVideoAdapter.ViewHolder>() {

    private var tempPosition: Int? = null

    private val mutableListVideoCache: MutableList<VideoCacheData> = mutableListOf()

    @OptIn(UnstableApi::class)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            ItemContainerVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    @OptIn(UnstableApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setupViewHolder(position, holder)
    }

    @OptIn(UnstableApi::class)
    private fun setupViewHolder(
        position: Int,
        holder: ViewHolder
    ) {

        var isSwipeBack = tempPosition != null && tempPosition!! < position
        tempPosition = position

        val findCache = mutableListVideoCache.find { data -> data.id == dataList[position].id }

        if (findCache == null) {

            mutableListVideoCache.clear()

            populateCache(isSwipeBack, position)

            holder.bindItem(dataList[position], mutableListVideoCache[0].exoPlayer)

        } else {

            holder.bindItem(dataList[position], findCache.exoPlayer)

        }
    }

    private fun populateCache(isSwipeBack: Boolean, position: Int) {

        val getElements =
            if (isSwipeBack) getNextElements(dataList, position) else getPreviousElements(
                dataList,
                position
            )

        mutableListVideoCache.add(
            VideoCacheData(
                id = dataList[position].id,
                exoPlayer = preloadVideo(dataList[position])
            )
        )
        getElements.forEach { data ->
            val finRes = dataList.find { res -> res.id == data.id }
            if (finRes != null) {
                mutableListVideoCache.add(
                    VideoCacheData(
                        id = finRes.id,
                        exoPlayer = preloadVideo(finRes)
                    )
                )
            }

        }
    }

    fun updateWithNewItems(item: MutableList<VideoEntity>) {
        val startIndex = dataList.size
        dataList.addAll(item)
        notifyItemRangeInserted(startIndex, dataList.size)
    }

    fun getList(): MutableList<VideoEntity> {
        return dataList
    }

    private fun getNextElements(list: List<VideoEntity>, index: Int): List<VideoEntity> {
        val result = mutableListOf<VideoEntity>()


        if (index < 0 || index >= list.size) {
            return result
        }


        for (i in 1..NUMBER_CONTENT_CACHE) {
            val nextIndex = index + i
            if (nextIndex < list.size) {
                result.add(list[nextIndex])
            } else {
                break
            }
        }

        return result
    }

    private fun getPreviousElements(list: List<VideoEntity>, index: Int): List<VideoEntity> {
        val result = mutableListOf<VideoEntity>()


        if (index <= 0 || index > list.size) {
            return result
        }


        for (i in 1..NUMBER_CONTENT_CACHE) {
            val previousIndex = index - i
            if (previousIndex >= 0) {
                result.add(list[previousIndex])
            } else {
                break
            }
        }

        return result
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    @OptIn(UnstableApi::class)
    private fun preloadVideo(file: VideoEntity): ExoPlayer {

        val exoPlayer = ExoPlayer.Builder(context).setLoadControl(
            DefaultLoadControl.Builder()
                .setPrioritizeTimeOverSizeThresholds(false)
                .setBufferDurationsMs(
                    DEFAULT_MIN_BUFFER_MS,
                    DEFAULT_MAX_BUFFER_MS,
                    DEFAULT_MIN_BUFFER_FOR_PLAY_BACK,
                    DEFAULT_MIN_BUFFER_AFTER_RE_BUFFER
                ).build()
        ).build()

        if (file.link.isNotEmpty()) {
            val factory: DataSource.Factory = DefaultDataSource.Factory(
                context
            )
            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()


            val mediaSource: MediaSource =
                ProgressiveMediaSource.Factory(factory, extractorsFactory)
                    .createMediaSource(MediaItem.fromUri(file.link))
            exoPlayer.seekTo(0)
            exoPlayer.playWhenReady = true

            exoPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)

                    if (playbackState == ExoPlayer.STATE_ENDED) {
                        exoPlayer.seekTo(0)
                    }

                }
            })

            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.prepare()
        }

        return exoPlayer
    }

    @UnstableApi
    inner class ViewHolder(var itemBinding: ItemContainerVideoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var exoHold: ExoPlayer


        fun bindItem(file: VideoEntity, exoPlayer: ExoPlayer) {
            itemBinding.textVideoTitle.text = file.name
            itemBinding.textVideoDescription.text = file.quality.toString()

            exoHold = exoPlayer

            itemBinding.videoView.setPlayer(exoPlayer)
            itemBinding.videoView.keepScreenOn = true
            itemBinding.videoView.setKeepContentOnPlayerReset(true)

            exoPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)

                    when (playbackState) {
                        ExoPlayer.STATE_BUFFERING -> {
                            itemBinding.containerProgress.visibility = View.VISIBLE
                        }

                        ExoPlayer.STATE_ENDED -> {
                            exoPlayer.seekTo(0)
                            itemBinding.containerProgress.visibility = View.GONE
                        }

                        ExoPlayer.STATE_IDLE -> {
                            itemBinding.containerProgress.visibility = View.GONE
                        }

                        ExoPlayer.STATE_READY -> {
                            itemBinding.containerProgress.visibility = View.GONE
                        }
                    }
                }
            })
        }


        fun releasePlayer() {
            exoHold.release()
        }
    }

    @OptIn(UnstableApi::class)
    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.releasePlayer()
    }
}


