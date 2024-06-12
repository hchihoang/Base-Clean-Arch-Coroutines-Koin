package com.inetkr.base.utils.extensions

import androidx.lifecycle.MutableLiveData
import com.inetkr.base.R
import com.inetkr.base.presentation.base.entity.BaseListLoadMoreResponse
import com.inetkr.base.presentation.base.entity.BaseListResponse
import com.inetkr.base.presentation.base.entity.BaseObjectResponse

typealias ObjectResponse<T> = MutableLiveData<BaseObjectResponse<T>>
typealias ListResponse<T> = MutableLiveData<BaseListResponse<T>>
typealias ListLoadMoreResponse<T> = MutableLiveData<BaseListLoadMoreResponse<T>>

typealias AndroidColors = android.R.color
typealias ProjectColors = R.color
