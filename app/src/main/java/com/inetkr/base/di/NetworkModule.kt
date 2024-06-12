package com.inetkr.base.di

import com.google.gson.GsonBuilder
import com.inetkr.base.BuildConfig
import com.inetkr.base.data.source.remote.APIService
import com.inetkr.base.data.source.remote.HeaderInterceptor
import com.inetkr.base.data.source.remote.TokenInterceptor
import com.inetkr.base.data.source.share_preference.SharePref
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 60L

val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get()) }

    single { createOkHttpClient(get()) }

}

fun createRetrofit(
    okHttpClient: OkHttpClient
): Retrofit {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
}

fun createOkHttpClient(sharedPref: SharePref): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    val tokenInterceptor = TokenInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(HeaderInterceptor(sharedPref))
        .addInterceptor(tokenInterceptor)
        .build()
}

fun createService(retrofit: Retrofit): APIService {
    return retrofit.create(APIService::class.java)
}
