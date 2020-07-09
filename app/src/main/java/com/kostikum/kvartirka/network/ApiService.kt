package com.kostikum.kvartirka.network

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService
@Inject constructor(retrofit: Retrofit) : Api {
    private val api by lazy { retrofit.create(Api::class.java) }

    override fun getCountries() = api.getCountries()

    override fun getFlatsByCity(city_id: Int) = api.getFlatsByCity(city_id)
}
