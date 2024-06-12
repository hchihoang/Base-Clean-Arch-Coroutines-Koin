package com.inetkr.base.di

import com.inetkr.base.data.source.share_preference.SharePref
import com.inetkr.base.data.source.share_preference.SharePrefImpl
import org.koin.dsl.module

val SharePrefModule = module {
    single<SharePref> { SharePrefImpl(get()) }
}