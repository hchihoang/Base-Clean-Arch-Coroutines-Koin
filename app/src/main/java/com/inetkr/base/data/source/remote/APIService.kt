package com.inetkr.base.data.source.remote

import com.inetkr.base.domain.entity.request.LoginRequest
import com.inetkr.base.domain.entity.request.RegisterRequest
import com.inetkr.base.domain.entity.response.LoginResponse
import com.inetkr.base.domain.entity.response.RegisterResponse
import com.inetkr.base.domain.entity.response.Note
import com.inetkr.base.presentation.base.entity.BaseListLoadMoreResponse
import com.inetkr.base.presentation.base.entity.BaseObjectResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {

    @GET("posts?order")
    suspend fun getNotes(@Query("page") page: Int?): BaseListLoadMoreResponse<Note>

    @POST("auth/token/")
    suspend fun login(@Body loginRequest: LoginRequest?): BaseObjectResponse<LoginResponse>

    @POST("auth/registerv2/")
    suspend fun signUp(@Body params: RegisterRequest?): BaseObjectResponse<RegisterResponse>

}