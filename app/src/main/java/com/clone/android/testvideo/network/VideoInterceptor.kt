package com.clone.android.testvideo.network

import com.clone.android.testvideo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class VideoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val newRequestBasic: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", BuildConfig.TEST_API_KEY)
            .build()


        return chain.proceed(
            newRequestBasic
        )

    }
}