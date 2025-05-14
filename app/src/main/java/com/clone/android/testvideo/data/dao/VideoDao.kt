package com.clone.android.testvideo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clone.android.testvideo.data.entities.VideoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface VideoDao {

    @Query("SELECT * from video_tbl")
    fun getAllData(): Flow<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gen: VideoEntity)

    @Query("DELETE from video_tbl")
    suspend fun deleteAll()

    @Query("DELETE from video_tbl where id=:id")
    suspend fun deleteById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<VideoEntity>)

    @Query("SELECT * from video_tbl where id=:id")
    fun getById(id: String): Flow<VideoEntity>

}