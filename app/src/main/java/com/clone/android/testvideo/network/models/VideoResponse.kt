package com.clone.android.testvideo.network.models

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("total_results") val totalResults: Int,
    val url: String,
    val videos: List<Video>
)