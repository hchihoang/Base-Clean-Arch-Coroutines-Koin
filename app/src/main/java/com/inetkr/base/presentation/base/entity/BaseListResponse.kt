package com.inetkr.base.presentation.base.entity

import com.google.gson.annotations.SerializedName
import com.inetkr.base.utils.Define

open class BaseListResponse<T>(
    val type: Int = 0,
    @SerializedName("results")
    val data: List<T>? = null,
    val error: Throwable? = null,
    var isShowingError:Boolean = true
) : BaseResponse() {
    open fun loading(): BaseListResponse<T>? {
        return BaseListResponse(Define.ResponseStatus.LOADING,null,null)
    }

    open fun success(data: List<T>?): BaseListResponse<T> {
        return BaseListResponse(Define.ResponseStatus.SUCCESS,data,null)
    }

    open fun error(throwable: Throwable,isShowingError:Boolean= true): BaseListResponse<T> {
        return BaseListResponse(Define.ResponseStatus.ERROR,null,throwable,isShowingError)
    }
}