package com.inetkr.base.presentation.base.entity


import com.google.gson.annotations.SerializedName

open class BaseResponse{
    @SerializedName("code") val status: Int? = null
    @SerializedName("msg") val msg: String? = null
}