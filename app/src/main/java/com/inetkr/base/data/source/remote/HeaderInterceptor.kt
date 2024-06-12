package com.inetkr.base.data.source.remote

import android.text.TextUtils
import android.util.Log
import com.inetkr.base.BuildConfig
import com.inetkr.base.data.source.share_preference.SharePref
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor(private val sharedPref: SharePref) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val headers: Headers
        val accessToken = sharedPref.savedUser?.accessToken
        headers = if (!TextUtils.isEmpty(accessToken)) {
            if(BuildConfig.DEBUG){
                Log.v("OkHttp", "Token $accessToken")
            }
            Headers.Builder()
                .add(
                    "Authorization",
                    "Bearer $accessToken"
                )
                .build()
        } else {
            Headers.Builder()
                .build()
        }

        val newRequest: Request =
            request.newBuilder()
                .headers(headers)
                .build()

        return chain.proceed(newRequest)
    }
}