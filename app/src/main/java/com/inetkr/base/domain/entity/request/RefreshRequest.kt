package com.inetkr.base.domain.entity.request

import com.google.gson.annotations.SerializedName

data class RefreshRequest (
    @SerializedName("refresh")
    var refresh:String? = null,
)