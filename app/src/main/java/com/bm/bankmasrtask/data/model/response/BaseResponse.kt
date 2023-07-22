package com.bm.bankmasrtask.data.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(@SerializedName("msg") val msg: String? = null,
                           @SerializedName("item") var data: T? = null,
                           @SerializedName("code") val code: Int? = null
)