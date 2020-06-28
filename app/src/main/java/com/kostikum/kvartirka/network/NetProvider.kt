package com.kostikum.kvartirka.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "http://api.kvartirka.com/client/1.4/"

object NetProvider {

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ApiService = retrofit.create(ApiService::class.java)
}
