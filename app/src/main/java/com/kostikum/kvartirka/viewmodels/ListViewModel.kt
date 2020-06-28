package com.kostikum.kvartirka.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kostikum.kvartirka.entity.Flat
import com.kostikum.kvartirka.entity.FlatsResponse
import com.kostikum.kvartirka.repository.Repository
import com.kostikum.kvartirka.util.Either
import com.kostikum.kvartirka.util.Failure
import com.kostikum.kvartirka.util.NetworkHandler
import kotlinx.coroutines.*

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.Network(NetworkHandler(application))
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
