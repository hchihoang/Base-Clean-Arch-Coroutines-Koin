package com.inetkr.base.data.source.remote

import android.util.Log
import com.google.gson.Gson
import com.inetkr.base.domain.entity.request.RequestError
import com.inetkr.base.utils.Define
import com.inetkr.base.utils.helper.Event
import com.inetkr.base.utils.helper.ObjectEventChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val mainRequest = chain.request()
        val ongoing = mainRequest.newBuilder()
        val mainResponse = chain.proceed(ongoing.build())
        if (mainResponse.code == Define.Api.Http.RESPONSE_CODE_ACCESS_TOKEN_EXPIRED) {
            runBlocking {
                CoroutineScope(Dispatchers.IO).launch {
                    ObjectEventChannel.sendEvent(Event.TokenExpire())
                }.join()
            }
        } else if (mainResponse.code == Define.Api.Http.RESPONSE_CODE_UPDATE_APP || mainResponse.code == Define.Api.Http.RESPONSE_CODE_MAINTENANCE) {
            val msg = mainResponse.body?.string().toString()
            val error = try {
                Gson().fromJson(msg, RequestError::class.java)
            } catch (e: Exception) {
                null
            }
            runBlocking {
                CoroutineScope(Dispatchers.IO).launch {
                    ObjectEventChannel.sendEvent(
                        Event.UpdateApp(
                            error?.errorMessage ?: "",
                            mainResponse.code
                        )
                    )
                }.join()
            }
        }
        return mainResponse
    }
}