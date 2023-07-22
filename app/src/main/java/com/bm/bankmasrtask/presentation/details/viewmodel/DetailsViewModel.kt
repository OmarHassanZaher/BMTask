package com.bm.bankmasrtask.presentation.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.bankmasrtask.data.model.response.HistoricalDataResponse
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.domain.usecase.latest.HistoricalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mHistoricalUseCase: HistoricalUseCase
) : ViewModel() {

    private val historicalLiveData: MutableLiveData<Resource<HistoricalDataResponse?>> = MutableLiveData()

    fun getHistoricalData(accessKey: String, fromCurrency: String, toCurrency: String, startDate: LocalDate, endDate: LocalDate) = viewModelScope.launch(Dispatchers.IO) {
        try {
            historicalLiveData.postValue((Resource.loading()))
            val result = mHistoricalUseCase.execute(accessKey, fromCurrency, toCurrency, startDate, endDate)
            historicalLiveData.postValue(result)
        } catch (e: Exception) {
            historicalLiveData.postValue(Resource.domainError(e))
        }
    }

    fun getHistoricalLiveData(): LiveData<Resource<HistoricalDataResponse?>> = historicalLiveData
}