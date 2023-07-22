package com.bm.bankmasrtask.domain.usecase.latest

import com.bm.bankmasrtask.data.model.response.LatestResponse
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.domain.mapper.ResourceMapper
import com.bm.bankmasrtask.util.ApiServices
import javax.inject.Inject

class LatestUseCase @Inject constructor(
    private val apiServices: ApiServices,
    private val mapper: ResourceMapper<LatestResponse?>
) {
    suspend fun execute(accessKey: String): Resource<LatestResponse?> = mapper.map(apiServices.getRates(accessKey))
}