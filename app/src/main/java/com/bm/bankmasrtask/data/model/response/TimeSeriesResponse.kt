package com.bm.bankmasrtask.data.model.response

data class TimeSeriesResponse(
    val success: Boolean,
    val error: FixerError?,
    val historical: Boolean?,
    val date: String?,
    val timestamp: Long?,
    val base: String?,
    val rates: Map<String, Double>?
){
    data class FixerError(
        val code: Int,
        val type: String,
        val info: String
    )
}
