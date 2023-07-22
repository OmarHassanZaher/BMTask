package com.bm.bankmasrtask.util

import com.bm.bankmasrtask.data.model.response.HistoricalDataResponse
import com.bm.bankmasrtask.data.model.response.LatestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("latest")
    suspend fun getRates(@Query("access_key") accessKey: String = "e30974634ea8ab00fd58c75b27e4385d"
                         ,@Query("format") format: Int = 1): Response<LatestResponse?>
    @GET("YYYY-MM-DD")
    suspend fun getHistorical(@Query("access_key") accessKey: String = "e30974634ea8ab00fd58c75b27e4385d"): Response<HistoricalDataResponse?>

}