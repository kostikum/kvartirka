package com.kostikum.kvartirka.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kostikum.kvartirka.entity.Flat
import com.kostikum.kvartirka.entity.FlatsResponse
import com.kostikum.kvartirka.repository.Repository
import com.kostikum.kvartirka.util.Either
import com.kostikum.kvartirka.util.Failure
import kotlinx.coroutines.*

class ListViewModel(private val repository: Repository) : ViewModel() {

    var flatList: MutableLiveData<List<Flat>> = MutableLiveData()
    var failure: MutableLiveData<Failure> = MutableLiveData()

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getDataUsingCityName(countryName: String = "", cityName: String = "") {
        viewModelScope.launch {
            if (countryName != "" && cityName != "") {
                val cityId: Int? = withContext(Dispatchers.IO) {
                    val response = repository.countries()
                    if (response is Either.Right) {
                        response.b.countries.firstOrNull { it.name == countryName }?.cities
                            ?.firstOrNull { it.name == cityName }?.id
                    } else null
                }
                getDataUsingCityId(cityId ?: 18)
            } else getDataUsingCityId()
        }
    }

    private suspend fun getDataUsingCityId(cityId: Int = 18) {
        val flats = withContext(Dispatchers.IO) { repository.flats(cityId) }
        withContext(Dispatchers.Main) { flats.fold(::handleFailure, ::handleFlatsResponse) }
    }

    private fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    private fun handleFlatsResponse(flatsResponse: FlatsResponse) {
        this.flatList.value = flatsResponse.flats
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
