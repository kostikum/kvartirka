package com.kostikum.kvartirka.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(val name: String, val cities: List<City>)
