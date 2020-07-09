package com.kostikum.kvartirka.network

import com.kostikum.kvartirka.entity.CountryResponse
import com.kostikum.kvartirka.entity.FlatsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("country")
    fun getCountries(): Call<CountryResponse>

    @GET("flats")
    fun getFlatsByCity(@Query("city_id") city_id: Int): Call<FlatsResponse>
}
