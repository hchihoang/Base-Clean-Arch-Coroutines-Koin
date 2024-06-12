package com.inetkr.base.presentation.ui.signup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.inetkr.base.R
import com.inetkr.base.data.source.share_preference.SharePref
import com.inetkr.base.domain.entity.request.RegisterError
import com.inetkr.base.domain.entity.request.RegisterErrorRequest
import com.inetkr.base.domain.entity.request.RegisterRequest
import com.inetkr.base.domain.entity.response.LoginResponse
import com.inetkr.base.domain.entity.response.RegisterResponse
import com.inetkr.base.domain.usecase.SignUpUseCase
import com.inetkr.base.domain.usecase.base.UseCaseResponse
import com.inetkr.base.presentation.base.BaseViewModel
import com.inetkr.base.presentation.base.entity.BaseObjectResponse
import com.inetkr.base.utils.extensions.ObjectResponse
import com.inetkr.base.utils.extensions.isEmailValid

class SignUpViewModel(
    private val application: Application,
    private val signUpUseCase: SignUpUseCase,
    private val sharePref: SharePref
) :
    BaseViewModel() {

    var registerData = ObjectResponse<RegisterResponse>()
    var registerErrorRequest = MutableLiveData<RegisterErrorRequest>()
    fun signUp(
        birthday: String?,
        username: String?,
        password: String?,
        passwordConfirm: String?
    ) {
        var isCancel = false
        var birthdayArraylist: ArrayList<String>? = null
        var usernameArraylist: ArrayList<String>? = null
        var passwordArraylist: ArrayList<String>? = null
        var confirmPasswordArraylist: ArrayList<String>? = null

        if (birthday.isNullOrEmpty()) {
            birthdayArraylist =
                arrayListOf(application.getString(R.string.str_is_empty))
            isCancel = true
        }
        if (username.isNullOrEmpty()) {
            usernameArraylist =
                arrayListOf(application.getString(R.string.str_is_empty))
            isCancel = true
        }
        if (!(username ?: " ").isEmailValid()) {
            usernameArraylist =
                arrayListOf(application.getString(R.string.str_this_email_isn_t_available))
            isCancel = true
        }
        if (password.isNullOrEmpty()) {
            passwordArraylist =
                arrayListOf(application.getString(R.string.str_is_empty))
            isCancel = true
        }
        if (passwordConfirm.isNullOrEmpty()) {
            confirmPasswordArraylist =
                arrayListOf(application.getString(R.string.str_is_empty))
            isCancel = true
        }
        if (password != passwordConfirm) {
            confirmPasswordArraylist =
                arrayListOf(application.getString(R.string.str_is_password_confirm_error))
            isCancel = true
        }
        if (isCancel) {
            val register = RegisterErrorRequest(
                RegisterError(
                    birthday = birthdayArraylist,
                    email = usernameArraylist,
                    password = passwordArraylist,
                    confirmPassword = confirmPasswordArraylist
                )
            )
            registerErrorRequest.value = register
            return
        }
        registerErrorRequest.value = RegisterErrorRequest()
        registerData.value = BaseObjectResponse<RegisterResponse>().loading()
        signUpUseCase.invoke(
            scope,
            RegisterRequest(
                birthday,
                username,
                password,
                passwordConfirm
            ),
            object : UseCaseResponse<BaseObjectResponse<RegisterResponse>> {

                override fun onSuccess(result: BaseObjectResponse<RegisterResponse>) {
                    result.data?.let {
                        sharePref.savedUser = LoginResponse(
                            id = it.id, accessToken = it.accessToken,
                            refreshToken = it.refreshToken
                        )
                        registerData.value = BaseObjectResponse<RegisterResponse>().success(it)
                    }
                }

                override fun onError(throwable: Throwable) {
                    registerData.value = BaseObjectResponse<RegisterResponse>().error(throwable)
                }
            })
    }

}