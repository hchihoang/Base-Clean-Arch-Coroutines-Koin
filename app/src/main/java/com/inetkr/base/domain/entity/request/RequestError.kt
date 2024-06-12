package com.inetkr.base.domain.entity.request

import com.google.gson.annotations.SerializedName
import com.inetkr.base.utils.Define

class RequestError {
    @SerializedName(Define.Api.ERROR_CODE)
    var errorCode: String? = null
    @SerializedName(Define.Api.ERROR_MESSAGE)
    var errorMessage: String? = null

}