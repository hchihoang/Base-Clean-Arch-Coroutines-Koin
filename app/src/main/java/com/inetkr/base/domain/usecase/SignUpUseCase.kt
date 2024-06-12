package com.inetkr.base.domain.usecase

import com.inetkr.base.data.repository.HomeRemoteRepository
import com.inetkr.base.domain.entity.request.LoginRequest
import com.inetkr.base.domain.entity.request.RegisterRequest
import com.inetkr.base.domain.entity.response.LoginResponse
import com.inetkr.base.domain.entity.response.RegisterResponse
import com.inetkr.base.domain.usecase.base.SingleUseCase
import com.inetkr.base.presentation.base.entity.BaseObjectResponse

class SignUpUseCase(
    private val homeRemoteRepository: HomeRemoteRepository
) : SingleUseCase<BaseObjectResponse<RegisterResponse>, RegisterRequest>() {

    override suspend fun run(params: RegisterRequest?): BaseObjectResponse<RegisterResponse> {
        return homeRemoteRepository.signUp(params)
    }
}