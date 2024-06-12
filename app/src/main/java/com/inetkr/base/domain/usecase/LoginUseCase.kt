package com.inetkr.base.domain.usecase

import com.inetkr.base.data.repository.HomeRemoteRepository
import com.inetkr.base.domain.entity.request.LoginRequest
import com.inetkr.base.domain.entity.response.LoginResponse
import com.inetkr.base.domain.usecase.base.SingleUseCase
import com.inetkr.base.presentation.base.entity.BaseObjectResponse

class LoginUseCase(
    private val homeRemoteRepository: HomeRemoteRepository
) : SingleUseCase<BaseObjectResponse<LoginResponse>, LoginRequest>() {


    override suspend fun run(params: LoginRequest?): BaseObjectResponse<LoginResponse> {
        return homeRemoteRepository.login(params)
    }
}