package com.clone.android.testvideo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clone.android.testvideo.data.dao.VideoDao
import com.clone.android.testvideo.data.entities.VideoEntity


@Database([VideoEntity::class], version = 1, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {


    abstract fun videoDao(): VideoDao

}