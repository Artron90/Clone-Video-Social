package com.clone.android.testvideo.repository

import com.clone.android.testvideo.data.dao.VideoDao
import com.clone.android.testvideo.data.entities.VideoEntity
import com.clone.android.testvideo.network.VideoAPI
import com.clone.android.testvideo.network.models.VideoResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class VideoRepository @Inject constructor(private val api: VideoAPI, private val videoDao: VideoDao) {

    fun getVideo(maxDuration: Int = 30,
                 page: Int = 1,
                 perPage: Int = 5): Observable<VideoResponse> =
        api.getVideos(maxDuration = maxDuration, page = page, perPage = perPage).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())

    fun getVideoData() = videoDao.getAllData()

    suspend fun insertVideoData(list: List<VideoEntity>) = videoDao.insertAll(list)

    suspend fun clearVideoData() = videoDao.deleteAll()

}