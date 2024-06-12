package com.inetkr.base.presentation.base.entity

import com.google.gson.annotations.SerializedName
import com.inetkr.base.utils.Define

class BaseListLoadMoreResponse<T> : BaseListResponse<T> {
    @SerializedName("next")
    var nextPage: Int? = null
    @SerializedName("count")
    var count: Int? = null
    @SerializedName("data")
    var dataResponse: DataResponse<T>? = null
    var isReFresh: Boolean = false
    var isLoadMore: Boolean = false

    constructor() {
    }

    constructor(
        type: Int,
        data: List<T>?,
        error: Throwable?,
        isRefresh: Boolean,
        isLoadmore: Boolean,
        isShowingError: Boolean = true
    ) : super(type, data, error) {
        this.isReFresh = isRefresh
        this.isLoadMore = isLoadmore
        this.isShowingError = isShowingError
    }

    override fun loading(): BaseListLoadMoreResponse<T>? {
        return BaseListLoadMoreResponse(
            Define.ResponseStatus.LOADING,
            null,
            null,
            isReFresh,
            isLoadMore
        )
    }

    fun success(
        data: List<T>?,
        isRefresh: Boolean,
        isLoadmore: Boolean
    ): BaseListLoadMoreResponse<T> {
        this.isLoadMore = isLoadmore
        this.isReFresh = isRefresh
        return BaseListLoadMoreResponse(
            Define.ResponseStatus.SUCCESS,
            data,
            null,
            isRefresh,
            isLoadmore
        )
    }

    override fun error(throwable: Throwable, isShowingError:Boolean): BaseListLoadMoreResponse<T> {
        return BaseListLoadMoreResponse(
            Define.ResponseStatus.ERROR,
            null,
            throwable,
            isReFresh,
            isLoadMore,
            isShowingError
        )
    }
}

data class DataResponse<T>(
    @field:SerializedName("next")
    val next: Int? = null,
    @field:SerializedName("previous")
    val previous: Int? = null,
    @field:SerializedName("count")
    val count: Int? = null,
    @SerializedName("results")
    val results: List<T>? = null,
)