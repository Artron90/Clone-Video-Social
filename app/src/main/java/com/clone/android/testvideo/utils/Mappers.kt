package com.clone.android.testvideo.utils

import com.clone.android.testvideo.data.entities.VideoEntity
import com.clone.android.testvideo.network.models.Video

class Mappers {

    open class FromVideoModelMapper {

        companion object {
            fun doMap(arg: Video): VideoEntity {
                val videoData = arg.videoFiles.first()
                return VideoEntity(
                    id = videoData.id,
                    quality = videoData.quality,
                    fileType = videoData.fileType,
                    width = videoData.width,
                    height = videoData.height,
                    link = videoData.link,
                    name = arg.user.name
                )
            }


            fun doMapList(arg: List<Video>): List<VideoEntity> {
                return arg.map { doMap(it) }
            }


        }


    }


}