package com.clone.android.testvideo.network.models

import com.google.gson.annotations.SerializedName

data class Video(
    val duration: Int,
    val height: Int,
    val id: Int,
    val image: String,
    val url: String,
    val user: User,
    @SerializedName("video_files") val videoFiles: List<VideoFile>,
    @SerializedName("video_pictures") val videoPictures: List<VideoPicture>,
    val width: Int
)