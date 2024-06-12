package com.inetkr.base.domain.entity.response

import com.google.gson.annotations.SerializedName


data class RegisterResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("refresh_token")
	val refreshToken: String,

	@field:SerializedName("access_token")
	val accessToken: String,

)