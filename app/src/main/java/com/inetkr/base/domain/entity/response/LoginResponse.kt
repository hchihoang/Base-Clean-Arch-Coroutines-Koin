package com.inetkr.base.domain.entity.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null
)