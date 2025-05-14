package com.clone.android.testvideo.utils

class DataOrException<T, E : Throwable>(
    var data: T? = null,
    var e: E? = null
)