package com.inetkr.base.presentation.ui.home

import com.inetkr.base.domain.entity.response.Note
import com.inetkr.base.domain.usecase.GetNotesUseCase
import com.inetkr.base.domain.usecase.base.UseCaseResponse
import com.inetkr.base.presentation.base.BaseViewModel
import com.inetkr.base.presentation.base.entity.BaseListLoadMoreResponse
import com.inetkr.base.utils.extensions.ListLoadMoreResponse

class HomeViewModel(private val getAlbumsUseCase: GetNotesUseCase) : BaseViewModel() {

    private var page: Int = 1
    val albumsReceivedLiveData = ListLoadMoreResponse<Note>()

    fun loadAlbums(isRefresh: Boolean = false) {
        if (isRefresh) {
            page = 1
        }
        getAlbumsUseCase.invoke(
            scope,
            page,
            object : UseCaseResponse<BaseListLoadMoreResponse<Note>> {

                override fun onSuccess(result: BaseListLoadMoreResponse<Note>) {
                    result.dataResponse?.let { data ->
                        page++
                        albumsReceivedLiveData.value = BaseListLoadMoreResponse<Note>().success(
                            result.dataResponse?.results,
                            isRefresh,
                            data.next != null
                        )
                    }
                }

                override fun onError(throwable: Throwable) {
                    albumsReceivedLiveData.value = BaseListLoadMoreResponse<Note>().error(throwable)
                }
            })
    }

}