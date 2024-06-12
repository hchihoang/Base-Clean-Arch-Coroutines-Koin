package com.inetkr.base.domain.usecase

import com.inetkr.base.data.repository.HomeRemoteRepository
import com.inetkr.base.domain.entity.response.Note
import com.inetkr.base.domain.usecase.base.SingleUseCase
import com.inetkr.base.presentation.base.entity.BaseListLoadMoreResponse

class GetNotesUseCase(
    private val homeRemoteRepository: HomeRemoteRepository
) : SingleUseCase<BaseListLoadMoreResponse<Note>, Int?>() {

    override suspend fun run(params: Int?): BaseListLoadMoreResponse<Note> {
        return homeRemoteRepository.getNotes(params)
    }
}