package com.bm.bankmasrtask.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.bankmasrtask.data.model.response.LatestResponse
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.domain.usecase.latest.LatestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mLatestUseCase: LatestUseCase,

    ) : ViewModel() {
    private val getRatesLiveData: MutableLiveData<Resource<LatestResponse?>> = MutableLiveData()

     fun getLatestData(accessKey: String,) = viewModelScope.launch(Dispatchers.IO) {
        try {
            getRatesLiveData.postValue((Resource.loading()))
            val result = mLatestUseCase.execute(accessKey)
            getRatesLiveData.postValue(result)
        } catch (e: Exception) {
            getRatesLiveData.postValue(Resource.domainError(e))
        }
    }
        private val exchangeRates = mapOf(
        "USD" to mapOf("EUR" to 0.85, "GBP" to 0.72, "JPY" to 110.89, "AUD" to 1.36, "CAD" to 1.25),
        "EUR" to mapOf("USD" to 1.18, "GBP" to 0.85, "JPY" to 130.89, "AUD" to 1.61, "CAD" to 1.48),
        "GBP" to mapOf("USD" to 1.38, "EUR" to 1.18, "JPY" to 153.88, "AUD" to 1.89, "CAD" to 1.74),
        "JPY" to mapOf(
            "USD" to 0.0090,
            "EUR" to 0.0076,
            "GBP" to 0.0065,
            "AUD" to 0.012,
            "CAD" to 0.011
        ),
        "AUD" to mapOf("USD" to 0.74, "EUR" to 0.62, "GBP" to 0.53, "JPY" to 81.22, "CAD" to 0.92),
        "CAD" to mapOf("USD" to 0.80, "EUR" to 0.68, "GBP" to 0.57, "JPY" to 89.66, "AUD" to 1.09)
    )

    fun getConversionRate(fromCurrency: String, toCurrency: String): Double {
        if (fromCurrency == toCurrency) {
            return 1.0
        }

        return exchangeRates[fromCurrency]?.get(toCurrency) ?: 0.0
    }
    fun getLatestLiveData(): LiveData<Resource<LatestResponse?>> = getRatesLiveData
}