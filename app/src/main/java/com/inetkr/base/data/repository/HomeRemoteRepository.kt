package com.inetkr.base.data.repository

import com.inetkr.base.data.source.remote.APIService
import com.inetkr.base.domain.entity.request.LoginRequest
import com.inetkr.base.domain.entity.request.RegisterRequest
import com.inetkr.base.domain.entity.response.LoginResponse
import com.inetkr.base.domain.entity.response.RegisterResponse
import com.inetkr.base.domain.entity.response.Note
import com.inetkr.base.presentation.base.entity.BaseListLoadMoreResponse
import com.inetkr.base.presentation.base.entity.BaseObjectResponse

class HomeRemoteRepository(private val apiService: APIService) {

    suspend fun getNotes(page: Int?): BaseListLoadMoreResponse<Note> {
        return apiService.getNotes(page)
    }

    suspend fun login(params: LoginRequest?): BaseObjectResponse<LoginResponse> {
        return apiService.login(params)
    }

    suspend fun signUp(params: RegisterRequest?): BaseObjectResponse<RegisterResponse> {
        return apiService.signUp(params)
    }
}