package com.inetkr.base.presentation.ui.splash

import com.inetkr.base.data.source.share_preference.SharePref
import com.inetkr.base.presentation.base.BaseViewModel

class SplashViewModel(private val sharePref: SharePref)  : BaseViewModel() {

    fun isUserLogin(): Boolean =
        sharePref.savedUser != null && !sharePref.savedUser?.accessToken.isNullOrEmpty()
}