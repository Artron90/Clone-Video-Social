package com.clone.android.testvideo.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_tbl")
data class VideoEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "quality")
    val quality: String,

    @ColumnInfo(name = "file_type")
    val fileType: String,

    @ColumnInfo(name = "width")
    val width: Int,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "link")
    val link: String,

    @ColumnInfo(name = "name")
    val name: String,

    )
