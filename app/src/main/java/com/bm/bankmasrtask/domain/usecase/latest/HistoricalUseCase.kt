package com.bm.bankmasrtask.domain.usecase.latest

import com.bm.bankmasrtask.data.model.response.HistoricalDataResponse
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.domain.mapper.ResourceMapper
import com.bm.bankmasrtask.util.ApiServices
import java.time.LocalDate
import javax.inject.Inject

class HistoricalUseCase  @Inject constructor(
    private val apiServices: ApiServices,
    private val mapper: ResourceMapper<HistoricalDataResponse?>
) {
    suspend fun execute(accessKey: String, from: String, to: String, startDate: LocalDate, endDate: LocalDate): Resource<HistoricalDataResponse?> = mapper.map(apiServices.getHistorical(accessKey))
}