package com.clone.android.testvideo.di

import android.content.Context
import androidx.room.Room
import com.clone.android.testvideo.data.VideoDatabase
import com.clone.android.testvideo.data.dao.VideoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): VideoDatabase =
        Room.databaseBuilder(context, VideoDatabase::class.java, "test_video_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideVideoDao(fcrDatabase: VideoDatabase): VideoDao = fcrDatabase.videoDao()


}