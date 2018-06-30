package org.mashup.takoyaki.data.remote.util


import android.util.Log

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkErrorInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = chain.proceed(request)

        var tryCount = 0
        while ((response == null || !response.isSuccessful) && tryCount < RETRY_COUNT) {
            Log.e(TAG, "intercept#request fail tryCount : $tryCount")

            try {
                Thread.sleep(RETRY_DELAY_TIME.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            tryCount++

            // Request 재 시도
            response = chain.proceed(request)
        }

        if (response == null) {
            throw IOException("REQUEST FAIL")
        }

        return response
    }

    companion object {

        private val TAG = NetworkErrorInterceptor::class.java.simpleName

        /**
         * 재시도 횟수
         */
        private val RETRY_COUNT = 3

        /**
         * 재시도 딜레이 타임
         */
        private val RETRY_DELAY_TIME = 5 * 1000
    }
}