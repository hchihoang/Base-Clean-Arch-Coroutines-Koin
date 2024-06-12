package com.inetkr.base.domain.entity.request

import com.google.gson.annotations.SerializedName

class RegisterRequest(
    @SerializedName("birthday")
    val birthday: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("confirm_password")
    var confirmPassword: String? = null,
)