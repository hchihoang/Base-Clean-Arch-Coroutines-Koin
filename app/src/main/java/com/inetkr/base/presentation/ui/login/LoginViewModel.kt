package com.inetkr.base.presentation.ui.login

import com.inetkr.base.data.source.share_preference.SharePref
import com.inetkr.base.domain.entity.request.LoginRequest
import com.inetkr.base.domain.entity.response.LoginResponse
import com.inetkr.base.domain.usecase.LoginUseCase
import com.inetkr.base.domain.usecase.base.UseCaseResponse
import com.inetkr.base.presentation.base.BaseViewModel
import com.inetkr.base.presentation.base.entity.BaseObjectResponse
import com.inetkr.base.utils.extensions.ObjectResponse

class LoginViewModel(private val loginUseCase: LoginUseCase, private val sharePref: SharePref) :
    BaseViewModel() {

    var loginData = ObjectResponse<LoginResponse>()

    fun login(userName: String, password: String) {
        if (userName.isEmpty() || password.isEmpty()) {
            return
        }
        loginData.value = BaseObjectResponse<LoginResponse>().loading()
        loginUseCase.invoke(
            scope,
            LoginRequest(userName, password),
            object : UseCaseResponse<BaseObjectResponse<LoginResponse>> {

                override fun onSuccess(result: BaseObjectResponse<LoginResponse>) {
                    result.data?.let {
                        sharePref.savedUser = it
                        loginData.value = BaseObjectResponse<LoginResponse>().success(it)
                    }
                }

                override fun onError(throwable: Throwable) {
                    loginData.value = BaseObjectResponse<LoginResponse>().error(throwable)
                }
            })
    }

}