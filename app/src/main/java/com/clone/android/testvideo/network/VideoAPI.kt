package com.clone.android.testvideo.network


import com.clone.android.testvideo.network.models.VideoResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface VideoAPI {

    @GET(value = "videos/popular")
    fun getVideos(
        @Query("max_duration") maxDuration: Int = 30,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 5
    ): Observable<VideoResponse>

}