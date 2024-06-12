package com.inetkr.base.domain.entity.request

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("username")
    var username:String? = null,
    @SerializedName("password")
    var password:String? = null
)