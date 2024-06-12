package com.inetkr.base.domain.entity.request

import com.google.gson.annotations.SerializedName
class RegisterErrorRequest(
    @SerializedName("error_data")
    var registerError: RegisterError? = null
)

class RegisterError(
    @SerializedName("birthday")
    val birthday: ArrayList<String>? = null,

    @SerializedName("email")
    var email: ArrayList<String>? = null,

    @SerializedName("password")
    var password: ArrayList<String>? = null,

    @SerializedName("confirm_password")
    var confirmPassword: ArrayList<String>? = null,
)
