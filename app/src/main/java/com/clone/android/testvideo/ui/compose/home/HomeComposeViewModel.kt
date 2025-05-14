package com.clone.android.testvideo.ui.compose.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clone.android.testvideo.repository.VideoRepository
import com.clone.android.testvideo.utils.DataOrException
import com.clone.android.testvideo.utils.Mappers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeComposeViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    private var page = 1

    var dataResponse = videoRepository.getVideoData()
    var progress = mutableStateOf(false)

    val compositeDisposable by lazy {
        CompositeDisposable()
    }

    fun setPage() {
        this.page += 1
    }

    fun getVideo(
        maxDuration: Int = 30,
        perPage: Int = 5
    ): MutableLiveData<DataOrException<Boolean, Throwable>?> {

        val result = MutableLiveData(DataOrException<Boolean, Throwable>())

        compositeDisposable.add(
            videoRepository.getVideo(maxDuration = maxDuration, page = page, perPage = perPage)
                .subscribe(
                    {


                        val data = Mappers.FromVideoModelMapper.doMapList(it.videos)
                        viewModelScope.launch(Dispatchers.IO) {
                            videoRepository.insertVideoData(data)
                        }

                    }, { error ->
                        result.postValue(DataOrException(e = error))
                    }
                )
        )

        return result
    }


    fun clearVideoData() {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.clearVideoData()
        }
    }


}