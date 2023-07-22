package com.bm.bankmasrtask.data.model.response

import kotlin.collections.*
import com.google.gson.annotations.SerializedName

data class HistoricalDataResponse(
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("historical")
    val historical: Boolean? = null,
    @SerializedName("rates")
    val rates: Rates? = null,
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("timestamp")
    val timestamp: Int? = null
) {
    data class Rates(
        @SerializedName("AUD")
        val aUD: Double? = null,
        @SerializedName("CAD")
        val cAD: Double? = null,
        @SerializedName("MXN")
        val mXN: Double? = null,
        @SerializedName("PLN")
        val pLN: Double? = null,
        @SerializedName("USD")
        val uSD: Double? = null
    )
}
//        : Map<String, Double?> by mapOf(
//        "AUD" to aUD,
//        "CAD" to cAD,
//        "MXN" to mXN,
//        "PLN" to pLN,
//        "USD" to uSD
//    )
