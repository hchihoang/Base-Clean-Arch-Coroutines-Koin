package com.inetkr.base.data.source.share_preference

import com.inetkr.base.domain.entity.response.LoginResponse

interface SharePref {
    var savedUser: LoginResponse?
    fun logout()

    var fcmToken: String

    var  language: String
}