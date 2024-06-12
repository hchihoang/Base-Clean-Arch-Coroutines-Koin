package com.inetkr.base.domain.entity.response

import com.google.gson.annotations.SerializedName

data class Note(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,
)